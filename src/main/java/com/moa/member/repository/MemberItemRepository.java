package com.moa.member.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.MemberItem;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
	List<MemberItem> findMemberItemByMemberId(UUID memberId);
}

