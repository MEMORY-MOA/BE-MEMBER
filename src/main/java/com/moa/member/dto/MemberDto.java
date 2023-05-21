package com.moa.member.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MemberDto extends BaseDto {

	private UUID memberId;
	private String loginId;
	private String pw;
	private String name;
	private String email;
	private String phone;

}
