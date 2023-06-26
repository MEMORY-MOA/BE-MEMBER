package com.moa.member.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.moa.member.controller.request.SignupRequest;
import com.moa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckNameValidator extends AbstractValidator<SignupRequest> {

	private final MemberRepository memberRepository;

	@Override
	protected void doValidate(SignupRequest dto, Errors errors) {
		if (memberRepository.existsMemberByNicknameAndDeletedAtIsNull(dto.getNickname())) {
			errors.rejectValue("name", "이름 중복 오류", "이미 사용 중인 이름입니다.");
		}
	}
}
