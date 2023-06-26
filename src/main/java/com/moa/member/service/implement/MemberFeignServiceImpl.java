package com.moa.member.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.moa.member.dto.MemberDto;
import com.moa.member.entity.Member;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.MemberFeignService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFeignServiceImpl implements MemberFeignService {

	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;

	@Override
	public MemberDto get(UUID memberId) {
		Member member = memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
			.orElseThrow(() -> new NotFoundException("회원정보가 없습니다."));
		return memberMapper.entityToDto(member);
	}

	@Override
	public List<MemberDto> getList(List<UUID> memberIdList) {
		List<Member> members = new ArrayList<>();

		for (UUID memberId : memberIdList) {
			members.add(memberRepository.findMemberByMemberIdAndDeletedAtIsNull(memberId)
				.orElseThrow(() -> new NotFoundException("회원정보가 없습니다.")));
		}

		return memberMapper.entityToDto(members);
	}
}
