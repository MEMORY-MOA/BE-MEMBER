package com.moa.member.service;

import com.moa.member.dto.MemberDto;
import com.moa.member.request.EmailRequestDto;
import com.moa.member.request.VerificationRequestDto;

public interface MemberService {

	void signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);

	void sendAuthEmail(EmailRequestDto request) throws Exception;

	void handleEmailVerification(VerificationRequestDto request);

}
