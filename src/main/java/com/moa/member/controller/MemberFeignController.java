package com.moa.member.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.response.TimeCapsuleMemberDto;
import com.moa.member.mapstruct.MemberFeignMapper;
import com.moa.member.service.MemberFeignService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal-members")
@RequiredArgsConstructor
@Slf4j
public class MemberFeignController {

	private final MemberFeignMapper memberFeignMapper;
	private final MemberFeignService memberFeignService;

	@GetMapping("{member-id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "회원 정보 조회 API_yejin", description = "타임캡슐 내 사용하는 api로, 프론트는 사용X")
	public TimeCapsuleMemberDto getMemberInfo(@PathVariable("member-id") UUID memberId) {
		return memberFeignMapper.toResponse(memberFeignService.get(memberId));
	}

	@GetMapping("/find-members")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "회원들 정보 조회 API_yejin", description = "타임캡슐 내 사용하는 api로, 프론트는 사용X")
	public List<TimeCapsuleMemberDto> getMembersInfo(@RequestParam("member-id") List<UUID> memberIdList) {
		return memberFeignMapper.toResponse(memberFeignService.getList(memberIdList));
	}
}
