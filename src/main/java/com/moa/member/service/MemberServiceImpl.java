package com.moa.member.service;

import java.util.Random;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moa.member.dto.MemberDto;
import com.moa.member.entity.Member;
import com.moa.member.exception.EmailSendException;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mastruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
import com.moa.member.request.EmailRequestDto;
import com.moa.member.request.VerificationRequestDto;
import com.moa.member.util.RedisUtil;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	private final RedisUtil redisUtil;
	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;

	private MimeMessage createMessage(String to, String code) throws Exception {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject("MOA 이메일 인증 안내");

		Context context = new Context();
		context.setVariable("code", code);

		String html = templateEngine.process("emailContents", context);
		helper.setText(html, true);

		return message;
	}

	public String createCode() {
		StringBuffer code = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) {
			int index = rnd.nextInt(3);

			switch (index) {
				case 0:
					code.append((char)((int)(rnd.nextInt(26)) + 97));
					break;
				case 1:
					code.append((char)((int)(rnd.nextInt(26)) + 65));
					break;
				case 2:
					code.append((rnd.nextInt(10)));
					break;
			}
		}
		return code.toString();
	}

	@Override
	public void sendAuthEmail(EmailRequestDto request) throws Exception {
		String email = request.getEmail();
		String code = createCode();
		MimeMessage message = createMessage(email, code);
		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new EmailSendException("이메일 전송에 실패했습니다.");
		}

		redisUtil.setDataExpire(email, code, 60 * 5L);
	}

	@Override
	public void handleEmailVerification(VerificationRequestDto request) {
		String email = request.getEmail();
		String verificationCode = request.getVerificationCode();

		String storedVerificationCode = redisUtil.getData(email);

		if (storedVerificationCode == null || !verificationCode.equals(storedVerificationCode)) {
			throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
		}

		redisUtil.deleteData(verificationCode);
	}

	@Override
	public void signUp(MemberDto memberDto) {
		Member member = MemberMapper.instance.dtoToEntity(memberDto);
		memberRepository.save(member);
	}

	@Override
	public boolean duplicateCheckLoginId(String loginId) {
		return memberRepository.existsMemberByLoginId(loginId);
	}

	@Override
	public boolean duplicateCheckName(String name) {
		return memberRepository.existsMemberByName(name);
	}

}
