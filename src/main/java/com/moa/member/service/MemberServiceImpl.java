package com.moa.member.service;

import com.moa.member.dto.MemberDto;
import com.moa.member.entity.Member;
import com.moa.member.mastruct.MemberMapper;
import com.moa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void SignUp(MemberDto memberDto) {
        Member member = MemberMapper.instance.dtoToEntity(memberDto);
        memberRepository.save(member);
    }
}
