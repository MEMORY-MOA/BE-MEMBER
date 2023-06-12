package com.moa.member.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.response.TimeCapsuleMemberDto;
import com.moa.member.dto.MemberDto;

@Mapper(componentModel = "spring")
public interface MemberFeignMapper {

	MemberMapper instance = Mappers.getMapper(MemberMapper.class);

	TimeCapsuleMemberDto toResponse(MemberDto dto);

	List<TimeCapsuleMemberDto> toResponse(List<MemberDto> dtos);
}
