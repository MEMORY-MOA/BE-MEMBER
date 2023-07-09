package com.moa.member.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.moa.member.dto.MemberItemDto;
import com.moa.member.dto.MemberItemIdsDto;
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

	@Override
	public MemberItemIdsDto findMemberItem(UUID memberId) {
		List<MemberItem> memberItemList = memberItemRepository.findMemberItemByMemberId(memberId);
		List<Integer> memberItemIdList = new ArrayList<>();
		for (MemberItem memberItem : memberItemList) {
			memberItemIdList.add(memberItem.getItemId());
		}
		return MemberItemIdsDto.builder()
			.itemIdList(memberItemIdList)
			.build();
	}
}
