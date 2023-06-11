package com.moa.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.request.DuplicateCheckRequest;
import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.SignupRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.mastruct.MemberMapper;
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
	public ResponseEntity<ResponseDto<Object>> signUp(@RequestBody @Valid SignupRequest signupRequest) {

		MemberDto memberDto = MemberMapper.instance.requestToDto(signupRequest);
		memberService.signUp(memberDto);

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("회원가입이 완료되었습니다.")
			.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/check-id")
	public ResponseEntity<ResponseDto<Object>> checkId(
		@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckLoginId(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 아이디입니다.")
				.build();
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 아이디입니다.")
				.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/check-name")
	public ResponseEntity<ResponseDto<Object>> checkName(
		@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckName(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 이름입니다.")
				.build();
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 이름입니다.")
				.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/send-email")
	public ResponseEntity<ResponseDto<?>> sendEmailVerification(@Valid @RequestBody EmailRequest request) {
		System.out.println(request);
		memberService.sendVerificationEmail(request);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("인증 코드 관련 이메일이 보내졌습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@GetMapping("/verify-code")
	public ResponseEntity<ResponseDto<?>> checkEmailVerification(
		@Valid @RequestBody VerificationRequest request) {
		memberService.verifyEmail(request);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("이메일 인증이 완료되었습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);

	}

}
