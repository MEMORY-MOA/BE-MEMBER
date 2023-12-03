package com.moa.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.member.controller.response.ResponseDto;

@RestController
@RequestMapping("/users")
public class IstioTestController {

	@GetMapping("/istio-test/ping-with-random-delay/fail")
	public ResponseEntity<ResponseDto<Object>> pingWithRandomDelayFail() throws InterruptedException {
		Thread.sleep(4000);

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.GATEWAY_TIMEOUT)
			.msg("Istio Test")
			.data(null)
			.build();
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(response);
	}

	@GetMapping("/istio-test/ping-with-random-delay/success")
	public ResponseEntity<ResponseDto<Object>> pingWithRandomDelaySuccess() throws InterruptedException {
		Thread.sleep(4000);

		ResponseDto response = ResponseDto.builder()
			.httpStatus(HttpStatus.OK)
			.msg("Istio Test")
			.data(null)
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
