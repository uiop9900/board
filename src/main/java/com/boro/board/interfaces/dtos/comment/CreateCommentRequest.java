package com.boro.board.interfaces.dtos.comment;

import com.boro.board.domain.comment.CommentCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

	@NotEmpty
	private Long parentCommentIdx; // 부모 댓글
	@NotEmpty
	private String comment; // 댓글내용
	@Nullable
	private Long tagMemberIdx; // 언급당한 회원

	public CommentCommand.Create toCommand() {
		return CommentCommand.Create.builder()
				.postIdx(parentCommentIdx)
				.content(comment)
				.tagMemberIdx(tagMemberIdx)
				.build();
	}

}
