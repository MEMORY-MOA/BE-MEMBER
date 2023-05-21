package com.moa.member.service;

import org.springframework.stereotype.Service;

import com.moa.member.dto.MemberDto;
import com.moa.member.entity.Member;
import com.moa.member.mastruct.MemberMapper;
import com.moa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public void signUp(MemberDto memberDto) {
		Member member = MemberMapper.instance.dtoToEntity(memberDto);
		memberRepository.save(member);
	}

	@Override
	public boolean duplicateCheckLoginId(String loginId) {
		return memberRepository.existsMemberByLoginId(loginId);
	}

	@Override
	public boolean duplicateCheckName(String name) {
		return memberRepository.existsMemberByName(name);
	}
}
