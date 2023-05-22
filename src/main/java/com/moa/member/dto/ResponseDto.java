package com.moa.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<D> {
	private final int code;
	private final String msg;
	private final D data;
}
