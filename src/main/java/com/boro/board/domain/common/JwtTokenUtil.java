package com.boro.board.domain.common;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

	private final static Long EXPIRE_TIME_MS = 3600L;

	@Value("${jwt.secret-key}")
	public static String secretKey;

	public static String createToken(String phoneNumber, String password) {
		// Claim = Jwt Token에 들어갈 정보
		Claims claims = Jwts.claims();
		claims.put("phoneNumber", phoneNumber);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_MS))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	// Claims에서 phoneNumber 꺼내기
	public static String getLoginId(String token, String secretKey) {
		return extractClaims(token, secretKey).get("phoneNumber").toString();
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
