package com.moa.member.repository;

import static com.moa.member.entity.QMember.*;
import static com.moa.member.entity.QFriend.*;

import com.moa.member.entity.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findMemberByFriendIdOrFriendNickname(String keyword, Pageable pageable) {
		JPAQuery<Member> query = queryFactory.selectFrom(member)
			.from(member)
			.join(friend)
			.on(member.memberId.eq(friend.memberId)) // 내 친구
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(friend.completed.eq(true))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc());

		return query.fetch();
	}
}
