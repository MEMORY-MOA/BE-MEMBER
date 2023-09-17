package com.moa.member.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LoginRequest {
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	@Pattern(regexp = "[a-zA-Z0-9]{2,9}",
		message = "아이디는 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
	private String loginId;

	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
		message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.")
	private String pw;
}
