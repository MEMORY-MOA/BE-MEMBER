package com.moa.member.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.env.Environment;
import com.moa.member.exception.ExistsException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.dto.ReissueTokenDto;
import com.moa.member.entity.Member;
import com.moa.member.exception.EmailSendException;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.MemberService;
import com.moa.member.util.EmailUtil;
import com.moa.member.util.RedisUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	private final RedisUtil redisUtil;
	private final JavaMailSender emailSender;
	private final EmailUtil emailUtil;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;

	@Override
	public void sendVerificationEmail(String email) {
		if (memberRepository.existsMemberByEmail(email)) throw new ExistsException("이메일 중복입니다.");
		String code = emailUtil.createCode();
		MimeMessage message = emailUtil.createMessage(email, code);
		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new EmailSendException("이메일 전송에 실패했습니다.");
		}

		redisUtil.setDataExpire(email, code, 60 * 5L);
	}

	@Override
	public void verifyEmail(VerificationRequest request) {
		String email = request.getEmail();
		String verificationCode = request.getVerificationCode();

		String storedVerificationCode = redisUtil.getData(email);

		if (storedVerificationCode == null) {
			throw new NotFoundException("유효하지 않은 인증 코드입니다.");
		} else if (!verificationCode.equals(storedVerificationCode)) {
			throw new NotFoundException("인증 코드가 일치하지 않습니다.");
		}

		redisUtil.deleteData(verificationCode);
	}

	@Override
	public void delete(UUID memberId) {
		Member member = memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));

		member.delete();
		memberRepository.save(member);
	}

	@Override
	public void checkPassword(UUID memberId, String pw) {
		if(!memberRepository.existsMemberByMemberIdAndPwAndDeletedAtIsNull(memberId, pw)) throw new NotFoundException("비밀번호가 일치하지 않습니다.");
	}

	@Override
	public void changePassword(UUID memberId, String pw) {
		Member member = memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));
		member.updatePw(pw);

		memberRepository.save(member);
	}

	@Override
	public MyPageDto findMyPage(UUID memberId) {
		Member member = memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));

		MyPageDto myPageDto = MemberMapper.instance.memberEntityToMypageDto(member);
		return myPageDto;
	}

	@Override
	public void modifyMyPage(UUID memberId, MyPageDto myPageDto) {
		Member member = memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));

		member = MemberMapper.instance.updateMemberEntityFromMyPageDto(member, myPageDto);
		memberRepository.save(member);
	}

	@Override
	public MemberDto signUp(MemberDto memberDto) {
		memberDto.setPw(bCryptPasswordEncoder.encode(memberDto.getPw()));
		Member member = MemberMapper.instance.dtoToEntity(memberDto);
		Member result = memberRepository.save(member);
		return memberMapper.entityToDto(result);
	}

	@Override
	public boolean duplicateCheckLoginId(String loginId) {
		return memberRepository.existsMemberByLoginIdAndDeletedAtIsNull(loginId);
	}

	@Override
	public boolean duplicateCheckName(String name) {
		return memberRepository.existsMemberByNicknameAndDeletedAtIsNull(name);
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) {
		Member member = memberRepository.findByLoginId(loginId);

		if (member == null) {
			throw new UsernameNotFoundException(loginId);
		}

		return new User(member.getLoginId(), member.getPw()
			, true, true, true, true
			, new ArrayList<>());
	}

	@Override
	public MemberDto getMemberDetailsByLoginId(String loginId) {
		Member member = memberRepository.findByLoginId(loginId);

		if (member == null) {
			throw new UsernameNotFoundException(loginId);
		}
		MemberDto memberDto = memberMapper.entityToDto(member);

		return memberDto;
	}

	@Override
	public ReissueTokenDto reissueAccessToken(String jwt) {
		String refreshToken = redisUtil.getData(jwt);
		String accessToken;
		if (refreshToken == null) {
			throw new NotFoundException("유효하지 않은 refresh Token입니다.");
		} else {
			String memberId = Jwts.parser().setSigningKey(env.getProperty("refreshToken.secret"))
				.parseClaimsJws(jwt).getBody()
				.getSubject();

			accessToken = Jwts.builder()
				.setSubject(memberId)
				.setExpiration(new Date(System.currentTimeMillis() +
					Long.parseLong(env.getProperty("accessToken.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("accessToken.secret"))
				.compact();
		}
		return ReissueTokenDto.builder()
			.accessToken(accessToken)
			.build();
	}
}
