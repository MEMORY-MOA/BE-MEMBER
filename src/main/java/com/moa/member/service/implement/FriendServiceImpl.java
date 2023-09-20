package com.moa.member.service.implement;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moa.member.dto.FriendDto;
import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.Friend;
import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.entity.Member;
import com.moa.member.exception.NotFoundException;
import com.moa.member.mapstruct.FriendMapper;
import com.moa.member.repository.FriendQueryRepository;
import com.moa.member.repository.FriendRepository;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.FriendService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	private final FriendQueryRepository friendQueryRepository;

	@Override
	public void friendRequest(FriendDto friendDto) {
		Optional<Member> result = memberRepository.findById(friendDto.getFriendId());
		result.orElseThrow(() -> new NotFoundException("친구 요청의 대상을 찾을 수 없습니다."));

		Friend friend1 = FriendMapper.instance.dtoToEntity(friendDto);
		friend1.sendFriendRequest();

		Friend friend2 = FriendMapper.instance.dtoToEntityInverse(friendDto);
		friend2.receiveFriendRequest();

		friendRepository.save(friend1);
		friendRepository.save(friend2);

	}

	@Override
	public void friendAccept(FriendDto friendDto) {
		Optional<Friend> result1 = friendRepository.findFriendByMemberIdAndFriendId(
			friendDto.getMemberId(), friendDto.getFriendId()
		);
		Optional<Friend> result2 = friendRepository.findFriendByMemberIdAndFriendId(
			friendDto.getFriendId(), friendDto.getMemberId()
		);

		result1.orElseThrow(() -> new NotFoundException("해당하는 친구 요청이 없습니다."));
		result2.orElseThrow(() -> new NotFoundException("해당하는 친구 요청이 없습니다."));

		Friend acceptedFriend1 = result1.get();
		Friend acceptedFriend2 = result2.get();

		acceptedFriend1.concludeFriendRequest();
		acceptedFriend2.concludeFriendRequest();

		friendRepository.save(acceptedFriend1);
		friendRepository.save(acceptedFriend2);
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

	@Override
	@Transactional
	public FriendsListDto getFriends(UUID memberId, Pageable pageable, FriendRequestStatus friendRequestStatus) {
		Page<FriendsListDto.FriendInfo> members = friendQueryRepository.findMemberByMemberIdAndFriendRequestStatus(
			memberId, friendRequestStatus, pageable);

		return FriendsListDto.builder()
			.friendsCnt(members.getTotalElements())
			.friendsPage(pageable.getPageNumber())
			.friendsList(members.getContent())
			.build();
	}

	@Override
	public FriendsListDto findFriends(String keyword, Pageable pageable) {
		Page<FriendsListDto.FriendInfo> members = friendQueryRepository.findMemberByFriendIdOrFriendNicknameAndFriendRequestStatus(
			keyword, pageable);

		//System.out.println(keyword);

		return FriendsListDto.builder()
			.friendsCnt(members.getTotalElements())
			.friendsPage(pageable.getPageNumber())
			.friendsList(members.getContent())
			.build();
	}

	@Override
	public FriendsListDto findMyFriends(UUID memberId, String keyword, Pageable pageable) {
		Page<Member> members = friendQueryRepository.findMemberByMemberIdAndFriendIdOrFriendNicknameAndFriendRequestStatus(
			memberId, keyword, pageable);

		return FriendsListDto.builder()
			.friendsCnt(members.getTotalElements())
			.friendsPage(pageable.getPageNumber())
			.friendsList(FriendMapper.instance.memberEntityToFriendInfo(members.getContent()))
			.build();
	}

}
