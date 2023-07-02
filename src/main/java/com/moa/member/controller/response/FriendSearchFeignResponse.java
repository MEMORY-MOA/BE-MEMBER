package com.moa.member.controller.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendSearchFeignResponse {
	private List<UUID> friendIdList;
}
