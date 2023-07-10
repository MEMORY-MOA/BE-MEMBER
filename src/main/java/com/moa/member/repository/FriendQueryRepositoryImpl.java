package com.moa.member.repository;

import static com.moa.member.entity.QFriend.*;
import static com.moa.member.entity.QMember.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.moa.member.dto.FriendsListDto;
import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findMemberByFriendIdOrFriendNickname(String keyword, Pageable pageable) {
		JPAQuery<Member> query = queryFactory.selectFrom(member)
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.memberId)) // 내 친구
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(friend.friendRequestStatus.eq(FriendRequestStatus.Concluded))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		return query.fetch();
	}

	@Override
	public List<FriendsListDto.FriendInfo> findMemberByMemberIdAndFriendRequestStatus(UUID memberId,
		FriendRequestStatus friendRequestStatus, Pageable pageable) {
		JPAQuery<FriendsListDto.FriendInfo> query = queryFactory
			.select(Projections.bean(FriendsListDto.FriendInfo.class,
				member.memberId,
				member.loginId,
				member.nickname,
				friend.friendRequestStatus
			))
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.friendId))
			.where(friend.memberId.eq(memberId))
			.where(friend.friendRequestStatus.eq(friendRequestStatus))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		return query.fetch();
	}
}
