package com.moa.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.MemberItem;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
}

