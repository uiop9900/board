package com.boro.board.interfaces.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LikePostRequest {

	@NotEmpty
	private String postIdx;

}
