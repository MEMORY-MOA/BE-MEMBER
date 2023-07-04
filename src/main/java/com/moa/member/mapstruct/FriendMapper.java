package com.moa.member.mapstruct;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.moa.member.controller.request.FriendRequest;
import com.moa.member.dto.FriendDto;
import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.Friend;
import com.moa.member.entity.Member;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendMapper {
	FriendMapper instance = Mappers.getMapper(FriendMapper.class);

	FriendDto requestToDto(UUID memberId, FriendRequest friendRequest);

	Friend dtoToEntity(FriendDto friendDto);

	@Mapping(source = "memberId", target = "friendId")
	@Mapping(source = "friendId", target = "memberId")
	Friend dtoToEntityInverse(FriendDto friendDto);

	FriendDto entityToDto(Friend friend);

	FriendsListDto.FriendInfo memberEntityToFriendInfo(Member member);

	List<FriendsListDto.FriendInfo> memberEntityToFriendInfo(List<Member> members);
}
