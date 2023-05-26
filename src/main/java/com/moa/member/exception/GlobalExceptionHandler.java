package com.moa.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moa.member.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ResponseDto<?>> handleValidationException(MethodArgumentNotValidException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(400)
			.msg("유효하지 않은 요청: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDto<?>> handleNotFoundException(NotFoundException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(ex.getCode())
			.msg("찾을 수 없음: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}

	@ExceptionHandler(EmailSendException.class)
	public ResponseEntity<ResponseDto<?>> handleEmailSendException(EmailSendException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(ex.getCode())
			.msg("이메일 전송 실패: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseDto<?>> handleRuntimeException(RuntimeException ex) {
		ResponseDto<?> responseDto = ResponseDto.builder()
			.code(500)
			.msg("내부 서버 오류: " + ex.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}
}
