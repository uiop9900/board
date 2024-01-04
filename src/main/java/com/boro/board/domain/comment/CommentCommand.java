package com.boro.board.domain.comment;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import com.sun.security.auth.UserPrincipal;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

public class CommentCommand {

	@Builder
	@Getter
	public static class Create {

		private String postIdx; // post의 idx
		private String parentCommentIdx; // 대댓글의 경우, 상위 댓글
		private String comment; // 댓글내용
		private String tagMemberIdx; // 언급당한 회원
		private String writerIdx; // 작성자

		public Comment toEntity(Post post, Comment parentComment, Member tagMember) {
			return Comment.builder()
					.post(post)
					.parentComment(parentComment)
					.content(comment)
					.tagMember(tagMember)
					.writer(UserPrincipal.get().getMember())
					.build();
		}


	}


}
