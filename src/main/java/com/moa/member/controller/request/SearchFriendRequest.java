package com.moa.member.controller.request;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFriendRequest {

	@NotBlank(message = "검색하려는 친구의 ID 혹은 닉네임을 입력해주십시오.")
	private String keyword;
	@Value("0")
	private int page;
	@Value("1000")
	private int pageSize;

}
