package com.moa.member.exception;

public class EmailSendException extends RuntimeException {
	private int code;

	public EmailSendException(String message) {
		super(message);
		this.code = 500;
	}

	public int getCode() {
		return code;
	}
}
