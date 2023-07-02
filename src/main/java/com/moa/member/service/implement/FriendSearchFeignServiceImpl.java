package com.moa.member.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.moa.member.dto.FriendIdListDto;
import com.moa.member.entity.Friend;
import com.moa.member.entity.Member;
import com.moa.member.mapstruct.FriendSearchFeignMapper;
import com.moa.member.repository.FriendRepository;
import com.moa.member.repository.MemberRepository;
import com.moa.member.service.FriendSearchFeignService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendSearchFeignServiceImpl implements FriendSearchFeignService {
	private final FriendSearchFeignMapper friendSearchFeignMapper;
	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;

	@Override
	public FriendIdListDto findFriendsIdByNickname(UUID memberId, String keyword) {
		List<UUID> memberIdList = new ArrayList<>();
		List<Friend> friendsList = friendRepository.findFriendByMemberIdAndCompleted(memberId, true);
		for (Friend friend : friendsList) {
			Member member = memberRepository.findMemberByMemberIdAndNicknameContaining(friend.getFriendId(), keyword)
				.orElse(null);
			if (member != null) {
				memberIdList.add(member.getMemberId());
			}
		}
		return FriendIdListDto.builder()
			.friendIdList(memberIdList)
			.build();
	}
}
