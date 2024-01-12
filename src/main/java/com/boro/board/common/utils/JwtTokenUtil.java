package com.boro.board.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ComponentScan("com.boro.board")
@RequiredArgsConstructor
public class JwtTokenUtil {

	private final static Long EXPIRE_TIME_MS = 720000L;

	public static String createToken(String secretKey, String email) {
		// Claim = Jwt Token에 들어갈 정보
		Claims claims = Jwts.claims();
		claims.put("email", email);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_MS))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	// Claims에서 phoneNumber 꺼내기
	public static String getLoginId(String token, String secretKey) {
		return extractClaims(token, secretKey).get("email").toString();
	}

	// 밝급된 Token이 만료 시간이 지났는지 체크
	public static boolean isExpired(String token, String secretKey) {
		Date expiredDate = extractClaims(token, secretKey).getExpiration();
		// Token의 만료 날짜가 지금보다 이전인지 check
		return expiredDate.before(new Date());
	}

	// SecretKey를 사용해 Token Parsing
	private static Claims extractClaims(String token, String secretKey) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
}
