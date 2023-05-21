package com.moa.member.controller;

import com.moa.member.dto.EmailRequestDto;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.dto.VerificationRequestDto;
import com.moa.member.exception.NotFoundException;
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
    @ResponseBody
    public ResponseDto<?> signUp(@RequestBody MemberDto memberDto){
        System.out.println(memberDto);
        memberService.SignUp(memberDto);
        ResponseDto responseDto= ResponseDto.builder()
                .code(200)
                .msg("Sign Up Successful")
                .build();
        return responseDto;
    }
	// 이메일 전송
	@PostMapping("/email")
	public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailRequestDto request) throws Exception {
		memberService.sendAuthEmail(request);
		return ResponseEntity.ok().build();
	}
	// 인증코드 확인
	@GetMapping("/verify")
	public ResponseEntity getVerify(@RequestBody @Valid VerificationRequestDto request) throws NotFoundException {
		try {
			memberService.verifyEmail(request);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
