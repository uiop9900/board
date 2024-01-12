package com.boro.board.interfaces.dtos.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignInRequest {

	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

}
