package com.boro.board.interfaces.dtos.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignInRequest {

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	private String password;

}
