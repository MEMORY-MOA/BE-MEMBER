package com.moa.member.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.response.FriendSearchFeignResponse;
import com.moa.member.mapstruct.FriendSearchFeignMapper;
import com.moa.member.service.FriendSearchFeignService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal-users")
@RequiredArgsConstructor
@Slf4j
public class FriendSearchFeignController {
	private final FriendSearchFeignMapper friendSearchFeignMapper;
	private final FriendSearchFeignService friendSearchFeignService;

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public FriendSearchFeignResponse searchFriendsIdByNickname(@RequestHeader UUID memberId,
		@RequestParam String keyword) {
		return friendSearchFeignMapper.dtoToResponse(
			friendSearchFeignService.findFriendsIdByNickname(memberId, keyword));
	}
}
