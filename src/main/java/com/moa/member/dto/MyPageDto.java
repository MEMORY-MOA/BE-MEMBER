package com.moa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MyPageDto extends BaseDto {

	private String name;
	private String email;
	private Boolean alarm;

}
