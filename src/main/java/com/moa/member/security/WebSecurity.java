package com.moa.member.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.moa.member.service.MemberService;
import com.moa.member.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurity {
	private final MemberService memberService;
	private final Environment env;
	private final RedisUtil redisUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationFilter authenticationFilter(
		AuthenticationManager authenticationManager) {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, memberService, env,
			redisUtil);

		SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
		authenticationFilter.setSecurityContextRepository(contextRepository);

		return authenticationFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/**").permitAll()
			.anyRequest().authenticated();
		// .and()
		// .formLogin()
		// .failureHandler(authenticationFailureHandler());

		AuthenticationManager authenticationManager = authenticationManager(
			http.getSharedObject(AuthenticationConfiguration.class));
		AuthenticationFilter authenticationFilter = authenticationFilter(authenticationManager);

		http.addFilterAt(authenticationFilter, AuthenticationFilter.class);
		return http.build();
	}
}
