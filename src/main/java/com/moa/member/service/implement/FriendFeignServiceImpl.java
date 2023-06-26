package com.moa.member.service.implement;

import com.moa.member.dto.FriendDto;
import com.moa.member.entity.Friend;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.FriendMapper;
import com.moa.member.repository.FriendRepository;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.FriendFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendFeignServiceImpl implements FriendFeignService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;

	@Override
	public void friendAccept(FriendDto friendDto) {

		memberRepository.findMemberByMemberIdAndDeletedAtIsNull(friendDto.getMemberId()).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
		memberRepository.findMemberByMemberIdAndDeletedAtIsNull(friendDto.getFriendId()).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

		Friend friend = FriendMapper.instance.dtoToEntity(friendDto);
		friend.changeCompletedStatus();

		friendRepository.save(friend);

	}
}
