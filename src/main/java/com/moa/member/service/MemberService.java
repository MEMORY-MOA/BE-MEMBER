package com.moa.member.service;

import java.util.UUID;

import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;

public interface MemberService {

	void signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);

	void sendAuthEmail(EmailRequest request);

	void handleEmailVerification(VerificationRequest request);

	public MyPageDto findMyPage(UUID memberId);

}
