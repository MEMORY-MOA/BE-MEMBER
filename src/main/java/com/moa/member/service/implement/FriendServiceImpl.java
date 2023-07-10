package com.moa.member.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moa.member.dto.FriendDto;
import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.Friend;
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

	@Override
	@Transactional
	public FriendsListDto getFriends(UUID memberId, int page, Pageable pageable, Boolean completed) {
		Optional<Page<Friend>> pages = friendRepository.findFriendsByMemberIdAndAndCompleted(memberId, completed,
			pageable);

		pages.orElseThrow(() -> new NotFoundException("친구 리스트 조회 중 오류가 발생했습니다."));
		if (pages.get().getTotalElements() <= 0)
			throw new NotFoundException("등록된 친구가 없습니다.");

		List<FriendsListDto.FriendInfo> friendsList = new ArrayList<>();
		for (Friend friend : pages.get().getContent()) {
			Optional<Member> result = memberRepository.findById(friend.getFriendId());
			result.orElseThrow(() -> new NotFoundException("등록된 친구 정보 오류로 리스트를 가져올 수 없습니다."));

			friendsList.add(FriendMapper.instance.memberEntityToFriendInfo(result.get()));
		}

		return FriendsListDto.builder()
			.friendsCnt((int)pages.get().getTotalElements())
			.friendsPage(page)
			.friendsList(friendsList)
			.build();
	}

	/*
		@Override
		public FriendsListDto findFriends(String keyword, int page, Pageable pageable) {
			Optional<Page<Member>> pages = memberRepository.findMemberByLoginIdContainingOrNicknameContaining(keyword);
			pages.orElseThrow(() -> new NotFoundException("검색 결과가 없습니다."));

			List<FriendsListDto.FriendInfo> friendsList = new ArrayList<>();
			for (Member member : pages.get().getContent()) {
				friendsList.add(FriendMapper.instance.memberEntityToFriendInfo(member));
			}

			return FriendsListDto.builder()
				.friendsCnt((int)pages.get().getTotalElements())
				.friendsPage(page)
				.friendsList(friendsList)
				.build();
		}
	*/
	@Override
	public FriendsListDto findMyFriends(String keyword, Pageable pageable) {
		List<Member> members = friendQueryRepository.findMemberByFriendIdOrFriendNickname(keyword, pageable);

		return FriendsListDto.builder()
			.friendsCnt(members.size())
			.friendsPage(pageable.getPageNumber())
			.friendsList(FriendMapper.instance.memberEntityToFriendInfo(members))
			.build();
	}

}
