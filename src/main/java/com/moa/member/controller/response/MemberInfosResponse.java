package com.moa.member.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MemberInfosResponse {
	private UUID memberId;
	private String loginId;
}
