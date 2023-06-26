package com.moa.member.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FriendFeignRequest {

	private UUID memberId;
	private UUID friendId;

}
