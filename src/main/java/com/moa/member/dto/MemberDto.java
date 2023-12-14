package com.moa.member.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MemberDto extends BaseDto {

	private UUID memberId;
	private String loginId;
	private String pw;
	private String nickname;
	private String email;
	private String phone;
	private int color;
	private LocalDateTime deletedAt;

}
