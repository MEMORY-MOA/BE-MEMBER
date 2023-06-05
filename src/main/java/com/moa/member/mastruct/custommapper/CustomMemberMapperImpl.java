package com.moa.member.mastruct.custommapper;

import org.springframework.stereotype.Component;

import com.moa.member.dto.MyPageDto;
import com.moa.member.entity.Member;

@Component
public class CustomMemberMapperImpl {
	public Member updateMemberEntityFromMyPageDto(Member existingMember, MyPageDto myPageDto) {
		if (myPageDto == null) {
			return null;
		}

		Member.MemberBuilder<?, ?> member = Member.builder();

		member.createdAt(myPageDto.getCreatedAt());
		member.modifiedAt(myPageDto.getModifiedAt());
		member.loginId(existingMember.getLoginId());
		member.memberId(existingMember.getMemberId());
		member.pw(existingMember.getPw());
		member.phone(existingMember.getPhone());

		if (myPageDto.getName() == null) {
			member.name(existingMember.getName());
		} else {
			member.name(myPageDto.getName());
		}
		if (myPageDto.getEmail() == null) {
			member.email(existingMember.getEmail());
		} else {
			member.email(myPageDto.getEmail());
		}
		if (myPageDto.getAlarm() == null) {
			member.alarm(existingMember.getAlarm());
		} else {
			member.alarm(myPageDto.getAlarm());
		}
		return member.build();
	}
}
