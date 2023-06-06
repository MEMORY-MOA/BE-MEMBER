package com.moa.member.service;

import com.moa.member.dto.FriendDto;

public interface FriendService {

	void friendRequest(FriendDto friendDto);

	void friendAccept(FriendDto friendDto);
}
