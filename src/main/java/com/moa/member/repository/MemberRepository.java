package com.moa.member.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	boolean existsMemberByLoginId(String loginId);

	boolean existsMemberByName(String name);

	// Optional<Member> findMemberByLoginId(String loginId);

	// Optional<Member> findMemberByName(String name);

}
