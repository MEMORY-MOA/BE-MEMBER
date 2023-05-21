package com.moa.member.service;

import com.moa.member.dto.EmailRequestDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.VerificationRequestDto;

public interface MemberService {
    void SignUp(MemberDto memberDto);
	void sendAuthEmail(EmailRequestDto request) throws Exception;
	void verifyEmail(VerificationRequestDto request);
}
