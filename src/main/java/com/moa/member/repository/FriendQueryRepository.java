package com.moa.member.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.entity.Member;

@Repository
public interface FriendQueryRepository {
	Page<FriendsListDto.FriendInfo> findMemberByFriendIdOrFriendNicknameAndFriendRequestStatus(String keyword, Pageable pageable);

	Page<FriendsListDto.FriendInfo> findMemberByMemberIdAndFriendRequestStatus(UUID memberId,
		FriendRequestStatus friendRequestStatus,
		Pageable pageable);

	Page<Member> findMemberByMemberIdAndFriendIdOrFriendNicknameAndFriendRequestStatus(UUID memberId, String keyword, Pageable pageable);
}
