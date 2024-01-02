package com.boro.board.domain.member;

import com.boro.board.domain.entity.Member;
import java.awt.print.PrinterJob;
import lombok.Builder;
import lombok.Getter;

public class MemberCommand {

	@Builder
	@Getter
	public static class SignUp {

		private String name;
		private String nickName;
		private String phoneNumber;
		private String password;

		public Member toEntity(String encryptPassword) {
			return Member.builder()
					.name(name)
					.nickName(nickName)
					.phoneNumber(phoneNumber)
					.password(encryptPassword)
					.build();
		}
	}



}
