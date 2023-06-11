package com.moa.member.util;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moa.member.exception.EmailSendException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailUtil {
	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;

	public MimeMessage createMessage(String to, String code) {
		try {

			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject("MOA 이메일 인증 안내");

			Context context = new Context();
			context.setVariable("code", code);

			String html = templateEngine.process("emailContents", context);
			helper.setText(html, true);

			return message;
		} catch (MessagingException ex) {
			throw new EmailSendException("이메일 생성에 실패했습니다.");
		}
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
}
