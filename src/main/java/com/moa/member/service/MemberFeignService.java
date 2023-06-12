package com.moa.member.service;

import java.util.List;
import java.util.UUID;

import com.moa.member.dto.MemberDto;

public interface MemberFeignService {
	MemberDto get(UUID loginId);

	List<MemberDto> getList(List<UUID> memberIdList);
}
