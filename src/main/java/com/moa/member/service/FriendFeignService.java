package com.moa.member.service;

import com.moa.member.dto.FriendDto;

public interface FriendFeignService {
	void friendAccept(FriendDto friendDto);
}
