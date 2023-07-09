package com.moa.member.service.implement;

import org.springframework.stereotype.Service;

import com.moa.member.dto.MemberItemDto;
import com.moa.member.entity.MemberItem;
import com.moa.member.mapstruct.MemberItemMapper;
import com.moa.member.repository.MemberItemRepository;
import com.moa.member.service.MemberItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberItemServiceImpl implements MemberItemService {
	private final MemberItemRepository memberItemRepository;
	private final MemberItemMapper memberItemMapper;

	@Override
	public void insertMemberItem(MemberItemDto memberItemDto) {
		MemberItem userItem = memberItemMapper.dtoToEntity(memberItemDto);
		memberItemRepository.save(userItem);
	}
}
