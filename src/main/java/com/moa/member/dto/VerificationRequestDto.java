package com.moa.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "이메일(필수)")
	private String email;
	@NotBlank(message = "인증 코드(필수)")
	private String verificationCode;

}
