package com.moa.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MyPageRequest {
	@Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,9}",
		message = "이름은 한글, 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
	private String nickname;
	@Email(message = "이메일 형식에 맞지 않습니다.")
	@Size(max = 100, message = "이메일은 최대 100자까지 입력 가능합니다.")
	private String email;
	private Boolean alarm;
}
