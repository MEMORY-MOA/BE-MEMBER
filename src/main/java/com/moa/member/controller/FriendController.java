package com.moa.member.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.request.FriendRequest;
import com.moa.member.dto.ResponseDto;
import com.moa.member.mastruct.FriendMapper;
import com.moa.member.service.FriendService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	@PostMapping("/request")
	public ResponseEntity<ResponseDto> friendRequest(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendRequest(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 보내졌습니다.");
	}

	@PostMapping("/accept")
	public ResponseEntity<ResponseDto> friendAccept(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendAccept(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 수락되었습니다.");
	}

	@DeleteMapping
	public ResponseEntity<ResponseDto> friendDeny(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendDeny(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 거절되었습니다.");
	}

	private ResponseEntity<ResponseDto> buildOkResponse(String msg) {

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg(msg)
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
