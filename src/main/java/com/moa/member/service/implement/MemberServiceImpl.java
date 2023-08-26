package com.moa.member.service.implement;

import java.util.UUID;

import com.moa.member.exception.ExistsException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.entity.Member;
import com.moa.member.exception.EmailSendException;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.MemberService;
import com.moa.member.util.EmailUtil;
import com.moa.member.util.RedisUtil;

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

	@Override
	public void sendVerificationEmail(EmailRequest request) {
		String email = request.getEmail();
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
		Member member = MemberMapper.instance.dtoToEntity(memberDto);

		if (memberRepository.existsMemberByEmail(memberDto.getEmail())) throw new ExistsException("이메일 중복입니다.");
		Member result = memberRepository.save(member);
		log.info("memberDto = {}", memberMapper.entityToDto(result));
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

}
