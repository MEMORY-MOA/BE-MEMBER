package com.moa.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moa.member.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {
	Optional<Friend> findFriendByMemberIdAndFriendId(UUID memberId, UUID friendId);

	Optional<Page<Friend>> findFriendsByMemberIdAndAndCompleted(UUID memberId, boolean completed, Pageable pageable);
}
