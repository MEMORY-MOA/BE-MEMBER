package com.moa.member.repository;

import com.moa.member.entity.FriendRequestStatus;
import com.moa.member.entity.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moa.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Member> findMemberByLoginIdOrNickname(String keyword, Pageable pageable) {
		JPAQuery<Member> query = queryFactory.selectFrom(member)
			.from(member)
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(member.deletedAt.isNull())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(member.nickname.asc(), member.loginId.asc());

		List<Member> memberList = query.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(member.count())
			.from(member)
			.where(member.loginId.contains(keyword).or(member.nickname.contains(keyword)))
			.where(member.deletedAt.isNull());

		return PageableExecutionUtils.getPage(memberList, pageable, countQuery::fetchOne);
	}
}
