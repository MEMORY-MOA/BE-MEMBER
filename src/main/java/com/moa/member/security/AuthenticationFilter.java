package com.moa.member.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.member.controller.request.LoginRequest;
import com.moa.member.dto.MemberDto;
import com.moa.member.service.MemberService;
import com.moa.member.util.RedisUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private MemberService memberService;
	private Environment env;
	private RedisUtil redisUtil;

	public AuthenticationFilter(AuthenticationManager authenticationManager, MemberService memberService,
		Environment env, RedisUtil redisUtil) {
		super(authenticationManager);
		this.memberService = memberService;
		this.env = env;
		this.redisUtil = redisUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getLoginId(),
					loginRequest.getPw(),
					new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		String userName = ((User)authResult.getPrincipal()).getUsername();
		MemberDto memberDto = memberService.getMemberDetailsByLoginId(userName);

		String accessToken = Jwts.builder()
			.setSubject(String.valueOf(memberDto.getMemberId()))
			.setExpiration(new Date(System.currentTimeMillis() +
				Long.parseLong(env.getProperty("accessToken.expiration_time"))))
			.signWith(SignatureAlgorithm.HS512, env.getProperty("accessToken.secret"))
			.compact();

		String refreshToken = Jwts.builder()
			.setSubject(String.valueOf(memberDto.getMemberId()))
			.setExpiration(new Date(System.currentTimeMillis() +
				Long.parseLong(env.getProperty("refreshToken.expiration_time"))))
			.signWith(SignatureAlgorithm.HS512, env.getProperty("refreshToken.secret"))
			.compact();

		redisUtil.setDataExpire(refreshToken, refreshToken, 60 * 60 * 24 * 14L);
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24 * 14);
		response.addHeader("accessToken", accessToken);
		response.addCookie(cookie);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
