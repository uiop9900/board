package com.boro.board.interfaces.dtos.like;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LikeCommentRequest {

	@NotEmpty
	private String commentIdx;

}
