package com.moa.member.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.moa.member.dto.FriendDto;
import com.moa.member.dto.FriendsListDto;

public interface FriendService {

	void friendRequest(FriendDto friendDto);

	void friendAccept(FriendDto friendDto);

	void friendDeny(FriendDto friendDto);

	FriendsListDto getFriends(UUID memberId, int page, Pageable pageable, Boolean completed);

	FriendsListDto findFriends(String keyword, int page, Pageable pageable);

}
