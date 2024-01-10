package com.boro.board.interfaces.dtos.member;

import com.boro.board.domain.member.MemberCommand.SignUp;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotEmpty
	private String name;

	@NotEmpty
	private String nickName;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	private String password;

	public SignUp toCommand() {
		return SignUp.builder()
			.name(name)
			.nickName(nickName)
			.phoneNumber(phoneNumber)
			.password(password)
			.build();
	}
}
