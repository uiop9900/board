package com.boro.board.common;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

	private final static String PASSWORD_RULE =  "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // 최소 8자, 하나 이상의 숫자, 문자, 특수 문자

	public static boolean checkPassword(String password) {
		return password.matches(PASSWORD_RULE);
	}


}
