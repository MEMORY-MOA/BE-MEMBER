package com.moa.member.controller;

import com.moa.member.controller.request.FriendFeignRequest;
import com.moa.member.controller.request.FriendRequest;
import com.moa.member.dto.FriendDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.service.FriendService;
import com.moa.member.mapstruct.FriendMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal-friends")
@RequiredArgsConstructor
@Slf4j
public class FriendFeignController {

	private final FriendService friendService;

	@PostMapping("/accept")
	@ResponseStatus(HttpStatus.OK)
	public void friendAccept(@RequestBody FriendFeignRequest friendFeignRequest) {
		friendService.friendAccept(FriendMapper.instance.feignRequestToDto(friendFeignRequest));
	}
}
