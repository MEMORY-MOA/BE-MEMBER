package com.moa.member.service;

import com.moa.member.dto.MemberDto;

public interface MemberService {
	void signUp(MemberDto memberDto);

	boolean duplicateCheckLoginId(String loginId);

	boolean duplicateCheckName(String name);
}
