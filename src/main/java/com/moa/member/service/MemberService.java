package com.moa.member.service;

import com.moa.member.dto.EmailRequestDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.VerificationRequestDto;

public interface MemberService {
	void signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);
  
  void sendAuthEmail(EmailRequestDto request) throws Exception;
  
	void verifyEmail(VerificationRequestDto request);
}
