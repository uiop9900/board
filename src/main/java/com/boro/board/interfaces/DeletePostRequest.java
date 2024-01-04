package com.boro.board.interfaces;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeletePostRequest {

	@NotEmpty
	private String postIdx;

}
