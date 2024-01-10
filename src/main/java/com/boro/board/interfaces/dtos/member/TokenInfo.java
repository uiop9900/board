package com.boro.board.interfaces.dtos.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TokenInfo {

	private String token;

}
