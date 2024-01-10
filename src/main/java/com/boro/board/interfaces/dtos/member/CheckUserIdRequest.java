package com.boro.board.interfaces.dtos.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CheckUserIdRequest {

	@NotEmpty
	private String phoneNumber; // id이자, 핸드폰 번호.

}
