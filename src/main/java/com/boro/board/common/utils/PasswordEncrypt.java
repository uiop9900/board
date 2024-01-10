package com.boro.board.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncrypt { // 현재까지 사용 중인 가장 강력한 해시 알고리즘 중 하나이다.

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public String encrypt(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword); // 비밀번호 일치 여부 확인
	}


}
