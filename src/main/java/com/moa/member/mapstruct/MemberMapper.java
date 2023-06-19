package com.moa.member.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.request.SignupRequest;
import com.moa.member.controller.response.MyPageResponse;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper instance = Mappers.getMapper(MemberMapper.class);

	MemberDto entityToDto(Member memberEntity);

	MyPageDto memberEntityToMypageDto(Member memberEntity);

	Member dtoToEntity(MemberDto memberDto);

	MemberDto requestToDto(SignupRequest signupRequest);

	List<MemberDto> entityToDto(List<Member> members);

	MyPageResponse myPageDtoToMyPageResponse(MyPageDto myPageDto);

}
