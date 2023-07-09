package com.moa.member.controller.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberItemIdsResponse {
	private List<Integer> itemIdList;
}
