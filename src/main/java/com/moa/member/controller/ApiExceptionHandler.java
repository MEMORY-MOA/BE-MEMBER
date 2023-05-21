package com.moa.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moa.member.controller.response.ResponseDto;
import com.moa.member.controller.validation.CheckLoginIdValidator;
import com.moa.member.controller.validation.CheckNameValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

	private final CheckLoginIdValidator checkLoginIdValidator;
	private final CheckNameValidator checkNameValidator;

	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkLoginIdValidator);
		binder.addValidators(checkNameValidator);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDto<Object> validityException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		ResponseDto<Object> response = ResponseDto.builder()
			.httpStatus(HttpStatus.BAD_REQUEST)
			.msg(bindingResult.getFieldErrors().get(0).getDefaultMessage())
			.build();

		return response;

	}
}
