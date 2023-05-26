package com.moa.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VerificationRequestDto {
	@Email
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Size(max = 100, message = "이메일은 최대 100자까지 입력 가능합니다.")
	private String email;
	@NotBlank(message = "인증 코드는 필수 입력 항목입니다.")
	private String verificationCode;

}
