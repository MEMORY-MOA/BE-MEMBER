package com.moa.member.entity;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@GeneratedValue
	@UuidGenerator
	@JdbcTypeCode(Types.VARCHAR)
	private UUID memberId;

	@Column(unique = true, nullable = false)
	private String loginId;
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
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Color color;

	public void delete() {
		deletedAt = LocalDateTime.now();
	}

	public void setColor() {
		int randomColor = new Random().nextInt(Color.values().length);
		this.color = Color.values()[randomColor];
	}
}
