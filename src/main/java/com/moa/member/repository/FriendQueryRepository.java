package com.moa.member.repository;

import com.moa.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendQueryRepository {
	List<Member> findMemberByFriendIdOrFriendNickname(String keyword, Pageable pageable);
}
