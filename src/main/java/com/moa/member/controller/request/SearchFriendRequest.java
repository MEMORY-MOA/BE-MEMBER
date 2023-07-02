package com.moa.member.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchFriendRequest {

	@NotBlank(message = "검색하려는 친구의 ID 혹은 닉네임을 입력해주십시오.")
	private String keyword;
}
