package com.boro.board.domain.config;

import static com.boro.board.domain.common.JwtTokenUtil.secretKey;

import com.boro.board.domain.common.JwtTokenFilter;
import com.boro.board.domain.common.JwtTokenUtil;
import com.boro.board.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {

	private final MemberService memberService;
	private final JwtTokenFilter jwtTokenFilter;

	// password 암호화
	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// filterChain을 직접 등록해줘야 한다.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션관리를 하지 않는다. -> 세션을 사용하지 않는다 -> 토큰 방식일떄 사용한다.
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(
						(authorizeRequests) -> authorizeRequests
								.requestMatchers(getPermitAllPaths()).permitAll()
								.anyRequest().authenticated()
				)
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

		return http.build();
	}


	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration corsConfig = new CorsConfiguration();

		corsConfig.addAllowedHeader(CorsConfiguration.ALL);
		corsConfig.addAllowedMethod(CorsConfiguration.ALL);
		corsConfig.addAllowedOriginPattern(CorsConfiguration.ALL);
		corsConfig.setAllowCredentials(true);

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	private String[] getPermitAllPaths() {
		return new String[]{
				"/member/test",
				"/member/sign-in",
				"/member/sign-up",
				"/member/nickname/**",
				"/member/phoneNumber",
				"/member/password"
		};
	}

}
