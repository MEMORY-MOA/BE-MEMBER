package com.moa.member.entity;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private UUID memberId;
	@Column(nullable = false)
	private UUID friendId;
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	FriendRequestStatus friendRequestStatus;

	public void concludeFriendRequest() {
		this.friendRequestStatus = FriendRequestStatus.Concluded;
	}

	public void sendFriendRequest() {
		this.friendRequestStatus = FriendRequestStatus.Sent;
	}

	public void receiveFriendRequest() {
		this.friendRequestStatus = FriendRequestStatus.Received;
	}
}
