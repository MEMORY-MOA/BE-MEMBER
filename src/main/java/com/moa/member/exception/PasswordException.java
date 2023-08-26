package com.moa.member.exception;

public class PasswordException extends RuntimeException {
	private int code;

	public PasswordException() {
		this.code = 400;
	}

	public int getCode() {
		return code;
	}
}
