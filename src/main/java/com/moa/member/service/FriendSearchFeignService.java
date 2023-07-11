package com.moa.member.service;

import java.util.UUID;

import com.moa.member.dto.FriendIdListDto;

public interface FriendSearchFeignService {
	public FriendIdListDto findFriendsIdByNickname(UUID memberId, String keyword);
}
