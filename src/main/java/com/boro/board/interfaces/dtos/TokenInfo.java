package com.boro.board.interfaces.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {

	private String grantType;
	private String accessToken;
	private String refreshToken;

}
