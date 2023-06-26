package com.moa.member.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.moa.member.controller.request.SignupRequest;
import com.moa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckLoginIdValidator extends AbstractValidator<SignupRequest> {

	private final MemberRepository memberRepository;

	@Override
	protected void doValidate(SignupRequest dto, Errors errors) {
		if (memberRepository.existsMemberByLoginIdAndDeletedAtIsNull(dto.getLoginId())) {
			errors.rejectValue("loginId", "아이디 중복 오류", "이미 사용 중인 아이디입니다.");
		}
	}
}
