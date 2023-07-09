package com.moa.member.service;

import java.util.UUID;

import com.moa.member.dto.MemberItemDto;
import com.moa.member.dto.MemberItemIdsDto;

public interface MemberItemService {
	void insertMemberItem(MemberItemDto memberItemDto);

	MemberItemIdsDto findMemberItem(UUID memberId);
}
