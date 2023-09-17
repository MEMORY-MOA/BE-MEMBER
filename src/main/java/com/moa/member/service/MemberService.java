package com.moa.member.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.dto.ReissueTokenDto;

public interface MemberService extends UserDetailsService {

	MemberDto signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);

	void sendVerificationEmail(EmailRequest request);

	public MyPageDto findMyPage(UUID memberId);

	void modifyMyPage(UUID memberId, MyPageDto myPageDto);

	void verifyEmail(VerificationRequest request);

	void delete(UUID memberId);

	MemberDto getMemberDetailsByLoginId(String userName);

	ReissueTokenDto reissueAccessToken(String jwt);
}
