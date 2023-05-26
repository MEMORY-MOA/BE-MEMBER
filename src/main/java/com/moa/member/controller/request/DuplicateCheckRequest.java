package com.moa.member.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DuplicateCheckRequest {
	@NotBlank(message = "중복 체크에 필요한 데이터를 입력해주십시오.")
	private String checkSubject;
}
