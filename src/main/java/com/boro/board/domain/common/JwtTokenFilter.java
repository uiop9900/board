package com.boro.board.domain.common;

import static com.boro.board.domain.common.ErrorMessage.NOT_CORRECT_TOKEN;
import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_TOKEN;
import static com.boro.board.domain.common.JwtTokenUtil.secretKey;

import com.boro.board.domain.exception.MemberException;
import com.boro.board.domain.member.MemberService;
import com.boro.board.infrastructure.member.Member;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // 매 요청마다 체크한다.

	private final MemberService memberService;


	@Override protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {

			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			// Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
			if (authorizationHeader == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
			if (!authorizationHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			// 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
			String token = authorizationHeader.split(" ")[1];

			// 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
			if (JwtTokenUtil.isExpired(token, secretKey)) {
				filterChain.doFilter(request, response);
				return;
			}

			// Jwt Token에서 loginId 추출
			String loginId = JwtTokenUtil.getLoginId(token, secretKey);

			// 추출한 loginId로 User 찾아오기
			Member loginUser = memberService.getMemberByPhoneNumber(loginId);

			// loginUser 정보로 UsernamePasswordAuthenticationToken 발급
		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				loginUser.getPhoneNumber(),
				null,
				List.of(new SimpleGrantedAuthority("ROLE_USER")));

			// 권한 부여
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}
}
