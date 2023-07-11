package com.moa.member.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.request.DuplicateCheckRequest;
import com.moa.member.controller.request.EmailRequest;
import com.moa.member.controller.request.MyPageRequest;
import com.moa.member.controller.request.SignupRequest;
import com.moa.member.controller.request.VerificationRequest;
import com.moa.member.controller.response.ResponseDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.mapstruct.MemberMapper;
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

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/check-id")
	public ResponseEntity<ResponseDto<Object>> checkId(
		@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckLoginId(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 아이디입니다.")
				.build();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 아이디입니다.")
				.build();
			return ResponseEntity.status(HttpStatus.OK).body(response);
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
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 이름입니다.")
				.build();
			return ResponseEntity.status(HttpStatus.OK).body(response);
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

	@GetMapping("/my-page")
	public ResponseEntity<ResponseDto<?>> viewMyPage(@RequestHeader("member") UUID memberId) {
		MyPageDto myPageDto = memberService.findMyPage(memberId);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("마이페이지 정보 조회가 완료되었습니다.")
			.data(MemberMapper.instance.myPageDtoToMyPageResponse(myPageDto))
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@PatchMapping("/my-page")
	public ResponseEntity<ResponseDto<?>> modifyMyPage(@RequestHeader("member") UUID memberId,
		@RequestBody @Valid MyPageRequest myPageRequest) {
		MyPageDto myPageDto = MemberMapper.instance.myPageRequestToMyPageDto(myPageRequest);
		memberService.modifyMyPage(memberId, myPageDto);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("마이페이지 정보 수정이 완료되었습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@DeleteMapping
	public ResponseEntity<ResponseDto> deleteMember(@RequestHeader("member") UUID memberId) {
		memberService.delete(memberId);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("회원이 탈퇴하였습니다")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}
}
