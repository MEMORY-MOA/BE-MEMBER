package com.moa.member.controller;

import com.moa.member.controller.request.FriendFeignRequest;
import com.moa.member.service.FriendFeignService;
import com.moa.member.mapstruct.FriendMapper;
import io.swagger.v3.oas.annotations.Operation;
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
	@Operation(summary = "친구초대수락 API_yejin", description = "타임캡슐 내 친구 초대 후 사용되는 api로, 프론트는 사용X")
	public void friendAccept(@RequestBody FriendFeignRequest friendFeignRequest) {
		friendFeignService.friendAccept(FriendMapper.instance.feignRequestToDto(friendFeignRequest));
	}
}
