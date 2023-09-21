package com.moa.member.controller;

import java.util.UUID;

import com.moa.member.controller.request.PasswordRequest;
import com.moa.member.controller.request.SearchFriendRequest;
import com.moa.member.dto.FriendsListDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.moa.member.dto.ReissueTokenDto;
import com.moa.member.mapstruct.MemberMapper;
import com.moa.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 가입, 로그인 등 회원관리 기능")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입_Inyoung.R")
	public ResponseEntity<ResponseDto<Object>> signUp(@RequestBody @Valid SignupRequest signupRequest) {

		MemberDto memberDto = MemberMapper.instance.requestToDto(signupRequest);
		MemberDto member = memberService.signUp(memberDto);

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("회원가입이 완료되었습니다.")
			.data(member.getMemberId())
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/check-id")
	@Operation(summary = "loginId 중복 체크 API_Inyoung.R")
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

	@PostMapping("/check-nickname")
	@Operation(summary = "닉네임 중복 체크 API_Inyoung.R")
	public ResponseEntity<ResponseDto<Object>> checkNickname(
		@RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {

		if (memberService.duplicateCheckName(duplicateCheckRequest.getCheckSubject())) {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.NOT_ACCEPTABLE)
				.msg("이미 사용 중인 닉네임입니다.")
				.build();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		} else {
			ResponseDto response = ResponseDto.builder()
				.httpStatus(HttpStatus.OK)
				.msg("사용 가능한 닉네임입니다.")
				.build();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@PostMapping("/send-email")
	@Operation(summary = "인증 코드 이메일로 발송하기_Ahin.K")
	public ResponseEntity<ResponseDto<?>> sendEmailVerification(@Valid @RequestBody EmailRequest request) {
		memberService.sendVerificationEmail(request.getEmail());
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("인증 코드 관련 이메일이 보내졌습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@PostMapping("/verify-code")
	@Operation(summary = "이메일 인증 코드 인증하기_Ahin.K")
	public ResponseEntity<ResponseDto<?>> checkEmailVerification(
		@Valid @RequestBody VerificationRequest request) {
		memberService.verifyEmail(request);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("이메일 인증이 완료되었습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);

	}

	@PostMapping("/verify-password")
	@Operation(summary = "비밀번호 일치 여부 확인_yejin")
	public ResponseEntity<ResponseDto<?>> verifyPassword(@RequestHeader("member") UUID memberId,
														 @RequestBody PasswordRequest request) {
		memberService.checkPassword(memberId, request.getPw());
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("비밀번호가 일치합니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@PatchMapping("/change-password")
	@Operation(summary = "비밀번호 변경_yejin")
	public ResponseEntity<ResponseDto<?>> changePassword(@RequestHeader("member") UUID memberId,
														 @RequestBody PasswordRequest request) {
		memberService.changePassword(memberId, request.getPw());
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("비밀번호를 변경하였습니다.")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@GetMapping("/my-page")
	@Operation(summary = "마이페이지 조회하기_Ahin.K")
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
	@Operation(summary = "마이페이지 수정하기_Ahin.K", description = "nickname, email, alarm 중에 수정하고 싶은 필드에만 값을 넣고 나머지는 null로 보내서 수정 가능")
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
	@Operation(summary = "회원탈퇴_Inyoung.R", description = "완벽히 탈퇴시키지는 않고 비활성화. 1년 후에 삭제 처리 하는 등 조치 필요할 듯.")
	public ResponseEntity<ResponseDto> deleteMember(@RequestHeader("member") UUID memberId) {
		memberService.delete(memberId);
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("회원이 탈퇴하였습니다")
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@GetMapping("/reissue")
	@Operation(summary = "access token 재발급하기")
	public ResponseEntity<ResponseDto<?>> reissueToken(
		@RequestHeader("Authorization") String token) {
		token = token.replace("Bearer ", "");
		ReissueTokenDto reissueTokenDto = memberService.reissueAccessToken(token);

		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("Access Token이 재발급 되었습니다.")
			.data(reissueTokenDto)
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}
}
