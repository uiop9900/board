package com.boro.board.common.filter;

import com.boro.board.common.config.SecretKeyConfig;
import com.boro.board.common.utils.JwtTokenUtil;
import com.boro.board.domain.member.MemberService;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.entity.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;



@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // 매 요청마다 체크한다.

	private final MemberService memberService;

	private final SecretKeyConfig secretKeyConfig;

	@Override protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {

			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			// Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
			// Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			// 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
			String token = authorizationHeader.split(" ")[1];

			// 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
			if (JwtTokenUtil.isExpired(token, secretKeyConfig.getSecretKey())) {
//				throw new RuntimeException("token시간이 만료되었습니다.");
				filterChain.doFilter(request, response);
				return;
			}

			// Jwt Token에서 loginId 추출
			String loginId = JwtTokenUtil.getLoginId(token, secretKeyConfig.getSecretKey());

			// 추출한 loginId로 User 찾아오기
		final Member member = memberService.getMemberByEmail(loginId);
		final UserPrincipal userPrincipal = UserPrincipal.toUserPrincipal(member);

		// loginUser 정보로 UsernamePasswordAuthenticationToken 발급
		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userPrincipal,
				authorizationHeader,
				List.of(new SimpleGrantedAuthority("ROLE_USER")));

			// 권한 부여
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		}
}
