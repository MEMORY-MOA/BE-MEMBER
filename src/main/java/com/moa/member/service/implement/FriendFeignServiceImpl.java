package com.moa.member.service.implement;

import org.springframework.stereotype.Service;

import com.moa.member.dto.FriendDto;
import com.moa.member.entity.Friend;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.FriendMapper;
import com.moa.member.repository.FriendRepository;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.FriendFeignService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendFeignServiceImpl implements FriendFeignService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;

	@Override
	public void friendAccept(FriendDto friendDto) {

		memberRepository.findMemberByMemberIdAndDeletedAt(friendDto.getMemberId(), null)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
		memberRepository.findMemberByMemberIdAndDeletedAt(friendDto.getFriendId(), null)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

		Friend friend = FriendMapper.instance.dtoToEntity(friendDto);
		friend.concludeFriendRequest();

		friendRepository.save(friend);

	}
}
