package com.moa.member.repository;

import static com.moa.member.entity.QFriend.*;
import static com.moa.member.entity.QMember.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
	public Page<FriendsListDto.FriendInfo> findMemberByFriendIdOrFriendNicknameAndFriendRequestStatus(String keyword,
		Pageable pageable) {
		JPAQuery<FriendsListDto.FriendInfo> query = queryFactory
			.select(Projections.bean(FriendsListDto.FriendInfo.class,
				member.memberId,
				member.loginId,
				member.nickname,
				member.memColor,
				friend.friendRequestStatus
			))
			.from(member)
			.leftJoin(friend)
			.on(member.memberId.eq(friend.memberId))
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(member.deletedAt.isNull())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		List<FriendsListDto.FriendInfo> memberList = query.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(member.count())
			.from(member)
			.leftJoin(friend)
			.on(member.memberId.eq(friend.memberId))
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(member.deletedAt.isNull());

		return PageableExecutionUtils.getPage(memberList, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<FriendsListDto.FriendInfo> findMemberByMemberIdAndFriendRequestStatus(UUID memberId,
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
			.where(member.deletedAt.isNull())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		List<FriendsListDto.FriendInfo> memberList = query.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(member.count())
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.friendId))
			.where(friend.memberId.eq(memberId))
			.where(friend.friendRequestStatus.eq(friendRequestStatus))
			.where(member.deletedAt.isNull());

		return PageableExecutionUtils.getPage(memberList, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<Member> findMemberByMemberIdAndFriendIdOrFriendNicknameAndFriendRequestStatus(UUID memberId,
		String keyword, Pageable pageable) {
		JPAQuery<Member> query = queryFactory.selectFrom(member)
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.friendId))
			.where(friend.memberId.eq(memberId)) // 내 정보
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(friend.friendRequestStatus.eq(FriendRequestStatus.Concluded))
			.where(member.deletedAt.isNull())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		List<Member> memberList = query.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(member.count())
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.memberId))
			.where(member.memberId.eq(memberId)) // 내 정보
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(friend.friendRequestStatus.eq(FriendRequestStatus.Concluded))
			.where(member.deletedAt.isNull());

		return PageableExecutionUtils.getPage(memberList, pageable, countQuery::fetchOne);

	}

}
