package com.moa.member.mapstruct;

import java.util.List;

import com.moa.member.controller.response.MemberInfosResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.request.MyPageRequest;
import com.moa.member.controller.request.SignupRequest;
import com.moa.member.controller.response.MyPageResponse;
import com.moa.member.dto.MemberDto;
import com.moa.member.dto.MyPageDto;
import com.moa.member.entity.Member;
import com.moa.member.mapstruct.custommapper.CustomMemberMapperImpl;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper instance = Mappers.getMapper(MemberMapper.class);

	MemberDto entityToDto(Member memberEntity);

	MyPageDto memberEntityToMypageDto(Member memberEntity);

	Member dtoToEntity(MemberDto memberDto);

	Member myPageDtoToMemberEntity(MyPageDto myPageDto);

	MemberDto requestToDto(SignupRequest signupRequest);

	MyPageDto myPageRequestToMyPageDto(MyPageRequest myPageRequest);

	List<MemberDto> entityToDto(List<Member> members);

	MyPageResponse myPageDtoToMyPageResponse(MyPageDto myPageDto);

	MemberInfosResponse dtoToMemberInfosResponse(MemberDto memberDto);

	List<MemberInfosResponse> dtoToMemberInfosResponse(List<MemberDto> memberDtos);

	default Member updateMemberEntityFromMyPageDto(Member existingMember, MyPageDto myPageDto) {
		CustomMemberMapperImpl customMapperImpl = new CustomMemberMapperImpl();
		return customMapperImpl.updateMemberEntityFromMyPageDto(existingMember, myPageDto);
	}
}
