package com.boro.board.interfaces.dtos;

import com.boro.board.domain.comment.CommentCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

	@NotEmpty
	private String postIdx; // post의 idx
	@NotEmpty
	private String comment; // 댓글내용
	@Nullable
	private String tagMemberIdx; // 언급당한 회원

	public CommentCommand.Create toCommand() {
		return CommentCommand.Create.builder()
				.postIdx(postIdx)
				.content(comment)
				.tagMemberIdx(tagMemberIdx)
				.build();
	}

}
