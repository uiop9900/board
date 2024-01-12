package com.boro.board.domain.member;

import lombok.Builder;
import lombok.Getter;

public class MemberCommand {

	@Builder
	@Getter
	public static class SignUp {

		private String name;
		private String nickName;
		private String email;
		private String password;

		public Member toEntity(String encryptPassword) {
			return Member.builder()
					.name(name)
					.nickName(nickName)
					.email(email)
					.password(encryptPassword)
					.build();
		}
	}



}
