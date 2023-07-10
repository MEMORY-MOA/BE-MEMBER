package com.moa.member.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	boolean existsMemberByLoginIdAndDeletedAtIsNull(String loginId);

	boolean existsMemberByNicknameAndDeletedAtIsNull(String nickname);

	List<Member> findMemberByNicknameContainingAndDeletedAtIsNull(String nickname);

	Optional<Member> findMemberByMemberIdAndDeletedAtIsNull(UUID memberId);
}
