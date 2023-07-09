package com.moa.member.mapstruct;

import java.util.UUID;

import org.mapstruct.Mapper;

import com.moa.member.controller.request.MemberItemRequest;
import com.moa.member.controller.response.MemberItemIdsResponse;
import com.moa.member.dto.MemberItemDto;
import com.moa.member.dto.MemberItemIdsDto;
import com.moa.member.entity.MemberItem;

@Mapper(componentModel = "spring")
public interface MemberItemMapper {
	MemberItemDto toDto(UUID memberId, MemberItemRequest userItemRequest);

	MemberItem dtoToEntity(MemberItemDto timeCapsuleItemDto);

	MemberItemIdsResponse dtoToResponse(MemberItemIdsDto memberItemIdsDto);
}

