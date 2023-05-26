package com.moa.member.controller;

import com.moa.member.dto.EmailRequestDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.dto.VerificationRequestDto;
import com.moa.member.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.request.DuplicateCheckRequest;
import com.moa.member.controller.request.SignupRequest;
import com.moa.member.controller.response.ResponseDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.mastruct.MemberMapper;

import com.moa.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseDto<?> signUp(@RequestBody @Valid SignupRequest signupRequest) {

		MemberDto memberDto = MemberMapper.instance.requestToDto(signupRequest);
		memberService.signUp(memberDto);

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("회원가입이 완료되었습니다.")
			.build();

	@PostMapping("/email")
	public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailRequestDto request) throws Exception {
		memberService.sendAuthEmail(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/verify")
	public ResponseEntity getVerify(@RequestBody @Valid VerificationRequestDto request) throws NotFoundException {
		try {
			memberService.verifyEmail(request);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		return response;
	}

	@PostMapping("/check-id")
	public ResponseDto<Object> checkId(@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckLoginId(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 아이디입니다.")
				.build();
			return response;
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 아이디입니다.")
				.build();
			return response;
		}
	}

	@PostMapping("/check-name")
	public ResponseDto<Object> checkName(@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckName(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 이름입니다.")
				.build();
			return response;
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 이름입니다.")
				.build();
			return response;
		}
	}

}
