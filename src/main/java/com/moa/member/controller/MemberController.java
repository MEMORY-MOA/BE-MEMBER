package com.moa.member.controller;

import com.moa.member.dto.MemberDto;
import com.moa.member.dto.ResponseDto;
import com.moa.member.entity.Member;
import com.moa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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





}
