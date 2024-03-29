package com.moa.member.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "BINARY(16)")
	private UUID memberId;

	@Column(unique = true, nullable = false)
	private String loginId;
	@Column(unique = true, nullable = false)
	private String pw;
	@Column(unique = true, nullable = false)
	private String nickname;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(unique = true, nullable = true)
	private String phone;
	@Column(unique = false, nullable = true)
	private Boolean alarm;
	@Column(nullable = true)
	private LocalDateTime deletedAt;
	private int memColor;

	public void delete() {
		deletedAt = LocalDateTime.now();
	}

	public void updatePw(String pw) {
		this.pw = pw;
	}
}
