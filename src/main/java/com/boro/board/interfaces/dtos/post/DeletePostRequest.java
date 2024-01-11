package com.boro.board.interfaces.dtos.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeletePostRequest {

	@NotEmpty
	private Long postIdx;

}
