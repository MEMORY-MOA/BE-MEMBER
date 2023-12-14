package com.moa.member.dto;

import java.util.List;
import java.util.UUID;

import com.moa.member.entity.FriendRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendsListDto {
	private long friendsCnt;
	private int friendsPage;
	private List<FriendInfo> friendsList;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class FriendInfo {
		private UUID memberId;
		private String loginId;
		private String nickname;
		private int color;
		private FriendRequestStatus friendRequestStatus;
	}
}
