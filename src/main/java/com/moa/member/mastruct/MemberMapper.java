package com.moa.member.mastruct;

import com.moa.member.dto.MemberDto;
import com.moa.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper instance = Mappers.getMapper(MemberMapper.class);

    MemberDto entityToDto(Member memberEntity);
    Member dtoToEntity(MemberDto memberDto);
}
