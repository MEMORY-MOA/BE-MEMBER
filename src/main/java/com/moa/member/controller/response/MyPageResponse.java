package com.moa.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MyPageResponse {

	private String loginId;
	private String nickname;
	private String email;
	private Boolean alarm;

}
