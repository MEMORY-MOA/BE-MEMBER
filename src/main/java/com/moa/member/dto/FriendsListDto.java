package com.moa.member.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendsListDto {
	private int friendsCnt;
	private int friendsPage;
	private List<FriendInfo> friendsList;

	public static class FriendInfo {
		private UUID memberId;
		private String nickname;

	}
}
