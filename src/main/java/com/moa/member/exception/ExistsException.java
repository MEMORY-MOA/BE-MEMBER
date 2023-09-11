package com.moa.member.exception;

public class ExistsException extends RuntimeException {
	private int code;

	public ExistsException(String message) {
		super(message);
		this.code = 400;
	}

	public int getCode() {
		return code;
	}
}
