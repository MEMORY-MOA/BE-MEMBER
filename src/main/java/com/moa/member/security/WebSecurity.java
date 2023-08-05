package com.moa.member.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeHttpRequests(
			auth -> auth.requestMatchers("/users/signup", "/users/check-id", "/users/check-nickname",
					"/users/send-email",
					"/users/verify-code", "/swagger-ui/**", "/swagger/**", "/swagger-ui.html",
					"/swagger-resources/**",
					"/v3/api-docs/**", "/webjars/**")
				.permitAll());
		return http.build();
	}
}
