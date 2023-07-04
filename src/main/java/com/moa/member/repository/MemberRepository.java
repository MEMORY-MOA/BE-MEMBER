package com.moa.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	boolean existsMemberByLoginId(String loginId);

	boolean existsMemberByNickname(String nickname);

	// Optional<Member> findMemberByLoginId(String loginId);

	// Optional<Member> findMemberByName(String name);

	Optional<Member> findMemberByMemberId(UUID memberId);

	//Optional<Page<Member>> findMemberByLoginIdContainingOrNicknameContaining(String keyword);
}
