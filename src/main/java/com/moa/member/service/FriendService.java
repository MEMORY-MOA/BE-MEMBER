package com.moa.member.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.moa.member.dto.FriendDto;
import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.FriendRequestStatus;

public interface FriendService {

	void friendRequest(FriendDto friendDto);

	void friendAccept(FriendDto friendDto);

	void friendDeny(FriendDto friendDto);

	FriendsListDto getFriends(UUID memberId, Pageable pageable, FriendRequestStatus friendRequestStatus);

	FriendsListDto findFriends(String keyword, Pageable pageable);

	FriendsListDto findMyFriends(UUID memberId, String keyword, Pageable pageable);
}
