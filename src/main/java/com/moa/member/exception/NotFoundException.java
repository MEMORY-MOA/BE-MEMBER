package com.moa.member.exception;

public class NotFoundException extends RuntimeException {

	private final int code;

	public int getCode() {
		return code;
	}

	public NotFoundException(int code) {
		this.code = code;
	}

	public NotFoundException(int code, String message) {
		super(message);
		this.code = code;
	}
}
