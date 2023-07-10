package com.moa.member.entity;

import java.sql.Types;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

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
	@JdbcTypeCode(Types.VARCHAR)
	private UUID memberId;
	@Column(nullable = false)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID friendId;
	@Column(nullable = false)
	boolean completed = false;

	public void changeCompletedStatus() {
		this.completed = !this.completed;
	}
}
