package com.moa.member.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.request.FriendRequest;
import com.moa.member.controller.request.SearchFriendRequest;
import com.moa.member.controller.response.ResponseDto;
import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.mapstruct.FriendMapper;
import com.moa.member.service.FriendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "친구 등록 및 조회", description = "친구 요청, 수락, 조회 등 API 입니다.")
public class FriendController {

	private final FriendService friendService;

	@PostMapping("/request")
	@Operation(summary = "친구 요청 보내기_Inyoung.R")
	public ResponseEntity<ResponseDto> friendRequest(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendRequest(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 보내졌습니다.");
	}

	@PostMapping("/accept")
	@Operation(summary = "친구 요청 수락하기_Inyoung.R")
	public ResponseEntity<ResponseDto> friendAccept(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendAccept(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 수락되었습니다.");
	}

	@DeleteMapping
	@Operation(summary = "친구 요청 거절하기 및 친구 삭제_Inyoung.R", description = "양방향으로 친구 관계 말소")
	public ResponseEntity<ResponseDto> friendDeny(@RequestHeader UUID memberId,
		@RequestBody @Valid FriendRequest friendRequest) {

		friendService.friendDeny(FriendMapper.instance.requestToDto(memberId, friendRequest));

		return buildOkResponse("친구 요청이 거절되었습니다.");
	}

	@GetMapping
	@Operation(summary = "요청을 보내 수락까지 완료된 친구 리스트 전체 조회_Inyoung.R", description = "page만 보낼 경우 자동으로 size 10, 닉네임 순으로 정렬한 결과를 보내줌.")
	public ResponseEntity<ResponseDto> getFriendsList(@RequestHeader UUID memberId,
		@PageableDefault(size = 10, sort = "nickname", direction = Sort.Direction.ASC) Pageable pageable) {

		FriendsListDto friendsList = friendService.getFriends(memberId, pageable, FriendRequestStatus.Concluded);

		return ResponseEntity.status(HttpStatus.OK).body(
			ResponseDto.<FriendsListDto>builder()
				.httpStatus(HttpStatus.OK)
				.msg("친구 리스트 검색을 완료했습니다.")
				.data(friendsList)
				.build()
		);
	}

	@GetMapping("/scent-requests/")
	@Operation(summary = "내가 보낸 친구 요청 리스트 조회_Inyoung.R", description = "page만 보낼 경우 자동으로 size 10, 닉네임 순으로 정렬한 결과를 보내줌.")
	public ResponseEntity<ResponseDto> getScentFriendsRequests(@RequestHeader UUID memberId,
		@PageableDefault(size = 10, sort = "nickname", direction = Sort.Direction.ASC) Pageable pageable) {

		FriendsListDto friendsList = friendService.getFriends(memberId, pageable, FriendRequestStatus.Scent);

		return ResponseEntity.status(HttpStatus.OK).body(
			ResponseDto.<FriendsListDto>builder()
				.httpStatus(HttpStatus.OK)
				.msg("내가 보낸 친구 요청 리스트 검색을 완료했습니다.")
				.data(friendsList)
				.build()
		);
	}

	@GetMapping("/received-requests/")
	@Operation(summary = "내가 받은 친구 요청 리스트 조회_Inyoung.R", description = "page만 보낼 경우 자동으로 size 10, 닉네임 순으로 정렬한 결과를 보내줌.")
	public ResponseEntity<ResponseDto> getReceivedFriendsRequests(@RequestHeader UUID memberId,
		@PageableDefault(size = 10, sort = "nickname", direction = Sort.Direction.ASC) Pageable pageable) {

		FriendsListDto friendsList = friendService.getFriends(memberId, pageable, FriendRequestStatus.Received);

		return ResponseEntity.status(HttpStatus.OK).body(
			ResponseDto.<FriendsListDto>builder()
				.httpStatus(HttpStatus.OK)
				.msg("친구 요청 리스트 검색을 완료했습니다.")
				.data(friendsList)
				.build()
		);
	}

	@PostMapping("/search")
	@Operation(summary = "키워드와 비슷한 닉네임, 혹은 로그인 아이디를 가진 회원 찾기_Inyoung.R", description = "page만 보낼 경우 자동으로 size 10, 닉네임 순으로 정렬한 결과를 보내줌. swagger사용시 searchFriendRequest와 pageable을 감싸는 json 괄호 지울것. 아니면 오류남.")
	public ResponseEntity<ResponseDto> searchFriends(
		@RequestBody @Valid SearchFriendRequest searchFriendRequest,
		@PageableDefault(size = 10, sort = "nickname", direction = Sort.Direction.ASC) Pageable pageable) {

		FriendsListDto friendsList = friendService.findFriends(searchFriendRequest.getKeyword(), pageable);

		return ResponseEntity.status(HttpStatus.OK).body(
			ResponseDto.<FriendsListDto>builder()
				.httpStatus(HttpStatus.OK)
				.msg("친구 검색을 완료했습니다.")
				.data(friendsList)
				.build()
		);
	}

	@PostMapping("/my-friends/search")
	public ResponseEntity<ResponseDto> searchMyFriends(@RequestHeader UUID memberId,
		@RequestBody @Valid SearchFriendRequest searchFriendRequest,
		@PageableDefault(size = 10, sort = "nickname", direction = Sort.Direction.ASC) Pageable pageable) {

		log.info(searchFriendRequest.getKeyword());
		FriendsListDto friendsList = friendService.findMyFriends(memberId, searchFriendRequest.getKeyword(), pageable);

		return ResponseEntity.status(HttpStatus.OK).body(
			ResponseDto.<FriendsListDto>builder()
				.httpStatus(HttpStatus.OK)
				.msg("친구 검색을 완료했습니다.")
				.data(friendsList)
				.build()
		);
	}

	private ResponseEntity<ResponseDto> buildOkResponse(String msg) {

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg(msg)
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
