package com.moa.member.service;

import java.util.Random;

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

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;
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
	private MimeMessage createMessage(String to, String ePw)throws Exception{

		MimeMessage  message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		// message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
		helper.setTo(to);
		helper.setSubject("MOA 이메일 인증 안내");//제목

		// 이메일 템플릿에 데이터 바인딩
		Context context = new Context();
		context.setVariable("code", ePw);

		String html = templateEngine.process("emailContents", context);
		helper.setText(html, true);

		return message;
	}
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
				case 0:
					key.append((char) ((int) (rnd.nextInt(26)) + 97));
					//  a~z  (ex. 1+97=98 => (char)98 = 'b')
					break;
				case 1:
					key.append((char) ((int) (rnd.nextInt(26)) + 65));
					//  A~Z
					break;
				case 2:
					key.append((rnd.nextInt(10)));
					// 0~9
					break;
			}
		}
		return key.toString();
	}
	@Override
	public void sendAuthEmail(EmailRequestDto request) throws Exception {
		String email = request.getEmail();
		// String subject = "제목";
		String ePw = createKey();
		MimeMessage message = createMessage(email, ePw);
		try{//예외처리
			emailSender.send(message);
		}catch(MailException es){
			es.printStackTrace();
			throw new IllegalArgumentException();
		}

		// 유효 시간(5분)동안 {email, authKey} 저장
		redisUtil.setDataExpire(email, ePw, 60 * 5L);
	}

	@Override
	public void verifyEmail(VerificationRequestDto request){
		String email = request.getEmail();
		String verificationCode = request.getVerificationCode();

		String storedVerificationCode = redisUtil.getData(email);
		if(storedVerificationCode==null || !verificationCode.equals(storedVerificationCode)) throw new NotFoundException(404, "Invalid Code");

		redisUtil.deleteData(verificationCode);
	}

}
