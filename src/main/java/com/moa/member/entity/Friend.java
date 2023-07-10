package com.moa.member.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	boolean completed = false;

	public void changeCompletedStatus() {
		this.completed = !this.completed;
	}
}
