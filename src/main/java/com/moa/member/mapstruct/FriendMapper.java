package com.moa.member.mapstruct;

import java.util.UUID;

import com.moa.member.controller.request.FriendFeignRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.request.FriendRequest;
import com.moa.member.dto.FriendDto;
import com.moa.member.entity.Friend;

@Mapper(componentModel = "spring")
public interface FriendMapper {
	FriendMapper instance = Mappers.getMapper(FriendMapper.class);

	FriendDto requestToDto(UUID memberId, FriendRequest friendRequest);

	Friend dtoToEntity(FriendDto friendDto);

	@Mapping(source = "memberId", target = "friendId")
	@Mapping(source = "friendId", target = "memberId")
	Friend dtoToEntityInverse(FriendDto friendDto);

	FriendDto entityToDto(Friend friend);

	FriendDto feignRequestToDto(FriendFeignRequest friendFeignRequest);
}
