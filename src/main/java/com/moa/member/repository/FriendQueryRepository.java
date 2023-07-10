package com.moa.member.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.entity.Member;

@Repository
public interface FriendQueryRepository {
	List<Member> findMemberByFriendIdOrFriendNickname(String keyword, Pageable pageable);

	List<FriendsListDto.FriendInfo> findMemberByMemberIdAndFriendRequestStatus(UUID memberId,
		FriendRequestStatus friendRequestStatus,
		Pageable pageable);
}
