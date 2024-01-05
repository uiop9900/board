package com.boro.board.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretKeyConfig {

	@Value("${jwt.secret-key}")
	public String secretKey;

	@Bean
	public String getSecretKey() {
		return secretKey;  // 여기서 설정한 secretKey를 반환해줌. 여기서는 사용하지 않음
	}
}
