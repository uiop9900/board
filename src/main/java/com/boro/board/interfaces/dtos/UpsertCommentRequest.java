package com.boro.board.interfaces.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpsertCommentRequest {

	@NotEmpty
	private String postIdx; // post의 idx
	@Nullable
	private String parentCommentIdx; // 대댓글의 경우, 상위 댓글
	@NotEmpty
	private String comment; // 댓글내용

}
