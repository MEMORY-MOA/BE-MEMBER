package com.moa.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moa.member.dto.ResponseDto;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ResponseDto<?>> handleValidationException(ValidationException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(400)
			.msg("Validation Error: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDto<?>> handleRuntimeException(NotFoundException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(404)
			.msg("Invalid Code Error: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseDto<?>> handleRuntimeException(RuntimeException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(500)
			.msg("Internal Server Error: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}
}
