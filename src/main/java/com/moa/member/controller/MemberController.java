package com.moa.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.dto.MemberDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.exception.NotFoundException;
import com.moa.member.request.EmailRequestDto;
import com.moa.member.request.VerificationRequestDto;
import com.moa.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	@ResponseBody
	public ResponseDto<?> signUp(@RequestBody MemberDto memberDto) {
		System.out.println(memberDto);
		memberService.SignUp(memberDto);
		ResponseDto responseDto = ResponseDto.builder()
			.code(200)
			.msg("Sign Up Successful")
			.build();
		return responseDto;
	}

	@PostMapping("/send-email")
	public ResponseEntity<ResponseDto<?>> sendEmailVerification(@Valid @RequestBody EmailRequestDto request) throws
		Exception {
		System.out.println(request);
		memberService.sendAuthEmail(request);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(200)
			.msg("인증 코드 관련 이메일이 보내졌습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@GetMapping("/verify-code")
	public ResponseEntity<ResponseDto<?>> checkEmailVerification(
		@Valid @RequestBody VerificationRequestDto request) throws
		NotFoundException {
		memberService.handleEmailVerification(request);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(200)
			.msg("이메일 인증이 완료되었습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

}
