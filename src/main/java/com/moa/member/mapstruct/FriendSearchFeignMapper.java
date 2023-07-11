package com.moa.member.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.response.FriendSearchFeignResponse;
import com.moa.member.dto.FriendIdListDto;

@Mapper(componentModel = "spring")
public interface FriendSearchFeignMapper {
	FriendSearchFeignMapper instance = Mappers.getMapper(FriendSearchFeignMapper.class);

	FriendSearchFeignResponse dtoToResponse(FriendIdListDto dto);
}
