package com.boro.board.interfaces;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CheckPasswordRequest {

	@NotEmpty
	private String password;

}
