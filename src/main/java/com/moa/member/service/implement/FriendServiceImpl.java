package com.moa.member.service.implement;

import java.util.Optional;

import com.moa.member.service.FriendService;
import org.springframework.stereotype.Service;

import com.moa.member.dto.FriendDto;
import com.moa.member.entity.Friend;
import com.moa.member.entity.Member;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.FriendMapper;
import com.moa.member.repository.FriendRepository;
import com.moa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;

	@Override
	public void friendRequest(FriendDto friendDto) {
		Optional<Member> result = memberRepository.findById(friendDto.getFriendId());
		result.orElseThrow(() -> new NotFoundException("친구 요청의 대상을 찾을 수 없습니다."));

		Friend friend1 = FriendMapper.instance.dtoToEntity(friendDto);
		friend1.changeCompletedStatus();

		Friend friend2 = FriendMapper.instance.dtoToEntityInverse(friendDto);

		friendRepository.save(friend1);
		friendRepository.save(friend2);

	}

	@Override
	public void friendAccept(FriendDto friendDto) {
		Optional<Friend> result = friendRepository.findFriendByMemberIdAndFriendId(
			friendDto.getMemberId(), friendDto.getFriendId()
		);
		result.orElseThrow(() -> new NotFoundException("해당하는 친구 요청이 없습니다."));

		Friend acceptedFriend = result.get();
		acceptedFriend.changeCompletedStatus();

		friendRepository.save(acceptedFriend);
	}

	@Override
	public void friendDeny(FriendDto friendDto) {
		Optional<Friend> result1 = friendRepository.findFriendByMemberIdAndFriendId(
			friendDto.getMemberId(), friendDto.getFriendId()
		);
		Optional<Friend> result2 = friendRepository.findFriendByMemberIdAndFriendId(
			friendDto.getFriendId(), friendDto.getMemberId()
		);

		result1.orElseThrow(() -> new NotFoundException("해당하는 친구 요청이 없습니다."));
		result2.orElseThrow(() -> new NotFoundException("해당하는 친구 요청이 없습니다."));

		friendRepository.delete(result1.get());
		friendRepository.delete(result2.get());
	}

}
