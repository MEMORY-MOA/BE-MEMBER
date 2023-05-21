package com.moa.member.exception;

public class NotFoundException extends RuntimeException {
	private int code;

	public NotFoundException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

