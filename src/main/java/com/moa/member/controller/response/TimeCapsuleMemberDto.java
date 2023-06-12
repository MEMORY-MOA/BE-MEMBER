package com.moa.member.controller.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeCapsuleMemberDto {
	private UUID memberId;

	private String loginId;

	private String nickname;
}
