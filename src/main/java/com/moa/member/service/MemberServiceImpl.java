package com.moa.member.service;

import java.util.Random;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moa.member.dto.EmailRequestDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.VerificationRequestDto;
import com.moa.member.entity.Member;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mastruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
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

	@Override
	public void SignUp(MemberDto memberDto) {
		Member member = MemberMapper.instance.dtoToEntity(memberDto);
		memberRepository.save(member);
	}

	private MimeMessage createMessage(String to, String code) throws Exception {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject("MOA 이메일 인증 안내");

		// 이메일 템플릿에 데이터 바인딩
		Context context = new Context();
		context.setVariable("code", code);

		String html = templateEngine.process("emailContents", context);
		helper.setText(html, true);

		return message;
	}

	public static String createCode() {
		StringBuffer code = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
				case 0:
					code.append((char)((int)(rnd.nextInt(26)) + 97));
					//  a~z  (ex. 1+97=98 => (char)98 = 'b')
					break;
				case 1:
					code.append((char)((int)(rnd.nextInt(26)) + 65));
					//  A~Z
					break;
				case 2:
					code.append((rnd.nextInt(10)));
					// 0~9
					break;
			}
		}
		return code.toString();
	}

	@Override
	public void sendAuthEmail(EmailRequestDto request) throws Exception {
		String email = request.getEmail();
		// String subject = "제목";
		String code = createCode();
		MimeMessage message = createMessage(email, code);
		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new RuntimeException("Failed to send email.");
		}

		// 유효 시간(5분)동안 {email, authKey} 저장
		redisUtil.setDataExpire(email, code, 60 * 5L);
	}

	@Override
	public void verifyEmail(VerificationRequestDto request) {
		String email = request.getEmail();
		String verificationCode = request.getVerificationCode();

		String storedVerificationCode = redisUtil.getData(email);
<<<<<<< HEAD
		if (storedVerificationCode == null || !verificationCode.equals(storedVerificationCode)) {
			throw new NotFoundException(404, "Invalid Code");
		}
=======
		if(storedVerificationCode==null || !verificationCode.equals(storedVerificationCode)) throw new NotFoundException(404, "Invalid Code");

>>>>>>> 44ed88f9ed331a5eef93e423cca494f1cbf33e14
		redisUtil.deleteData(verificationCode);
	}
}
