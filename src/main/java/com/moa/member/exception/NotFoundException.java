package com.moa.member.exception;

public class NotFoundException extends RuntimeException {
	private int code;

	public NotFoundException(String message) {
		super(message);
		this.code = 404;
	}

	public int getCode() {
		return code;
	}
}
