package com.boro.board.interfaces.dtos;

import com.boro.board.domain.comment.CommentCommand;
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
	@Nullable
	private String tagMemberIdx; // 언급당한 회원
	@NotEmpty
	private String writerIdx; // 작성자

	public CommentCommand.Create toCommand() {
		return CommentCommand.Create.builder()
				.postIdx(postIdx)
				.parentCommentIdx(parentCommentIdx)
				.comment(comment)
				.tagMemberIdx(tagMemberIdx)
				.writerIdx(writerIdx)
				.build();
	}

}
