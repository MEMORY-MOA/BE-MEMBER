package com.moa.member.repository;

import com.moa.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberQueryRepository {
	Page<Member> findMemberByLoginIdOrNickname(String keyword, Pageable pageable);
}
