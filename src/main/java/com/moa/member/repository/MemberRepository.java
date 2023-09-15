package com.moa.member.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	boolean existsMemberByLoginIdAndDeletedAt(String loginId, LocalDateTime deletedAt);

	boolean existsMemberByNicknameAndDeletedAt(String nickname, LocalDateTime deletedAt);

	List<Member> findMemberByNicknameContainingAndDeletedAt(String nickname, LocalDateTime deletedAt);

	Optional<Member> findMemberByMemberIdAndDeletedAt(UUID memberId, LocalDateTime deletedAt);

	Optional<Page<Member>> findMemberByLoginIdContainingOrNicknameContaining(String loginId, String nickname,
		Pageable pageable);

	boolean existsMemberByEmail(String email);

	boolean existsMemberByMemberIdAndPwAndDeletedAt(UUID memberId, String password, LocalDateTime deletedAt);
}
