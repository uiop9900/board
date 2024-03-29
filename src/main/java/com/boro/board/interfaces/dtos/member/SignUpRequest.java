package com.boro.board.interfaces.dtos.member;

import com.boro.board.domain.member.MemberCommand.SignUp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotEmpty
	private String name;

	@NotEmpty
	private String nickName;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String password;

	public SignUp toCommand() {
		return SignUp.builder()
			.name(name)
			.nickName(nickName)
			.email(email)
			.password(password)
			.build();
	}
}
