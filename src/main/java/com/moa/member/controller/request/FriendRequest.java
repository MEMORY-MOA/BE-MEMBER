package com.moa.member.controller.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendRequest {

	@NotNull(message = "대상이 되는 친구의 UUID를 입력해주십시오.")
	private UUID friendId;
}
