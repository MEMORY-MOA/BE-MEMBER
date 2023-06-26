package com.moa.member.controller;

import com.moa.member.controller.request.FriendFeignRequest;
import com.moa.member.service.FriendFeignService;
import com.moa.member.mapstruct.FriendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal-friends")
@RequiredArgsConstructor
@Slf4j
public class FriendFeignController {

	private final FriendFeignService friendFeignService;

	@PostMapping("/acceptance")
	@ResponseStatus(HttpStatus.OK)
	public void friendAccept(@RequestBody FriendFeignRequest friendFeignRequest) {
		log.info(String.valueOf(friendFeignRequest.getFriendId()));
		friendFeignService.friendAccept(FriendMapper.instance.feignRequestToDto(friendFeignRequest));
	}
}
