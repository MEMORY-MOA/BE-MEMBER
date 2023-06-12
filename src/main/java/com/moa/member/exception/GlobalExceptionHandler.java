package com.moa.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moa.member.controller.validation.CheckLoginIdValidator;
import com.moa.member.controller.validation.CheckNameValidator;
import com.moa.member.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final CheckLoginIdValidator checkLoginIdValidator;
	private final CheckNameValidator checkNameValidator;

	@InitBinder("signupRequest")
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkLoginIdValidator);
		binder.addValidators(checkNameValidator);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDto<Object> validityException(
		MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		ResponseDto<Object> response = ResponseDto.builder()
			.httpStatus(HttpStatus.BAD_REQUEST)
			.msg(bindingResult.getFieldErrors().get(0).getDefaultMessage())
			.build();

		return response;

	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDto<?>> handleNotFoundException(NotFoundException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.NOT_FOUND)
			.msg("찾을 수 없음: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}

	@ExceptionHandler(EmailSendException.class)
	public ResponseEntity<ResponseDto<?>> handleEmailSendException(EmailSendException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.msg("이메일 전송 실패: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseDto<?>> handleRuntimeException(RuntimeException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.msg("내부 서버 오류: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}
}
