package com.moa.member.service;

import com.moa.member.controller.request.EmailRequestDto;
import com.moa.member.controller.request.VerificationRequestDto;
import com.moa.member.dto.MemberDto;

public interface MemberService {

	void signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);

	void sendAuthEmail(EmailRequestDto request) throws Exception;

	void handleEmailVerification(VerificationRequestDto request);

}
