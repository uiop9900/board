package com.boro.board.domain.comment;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

public class CommentCommand {

	@Builder
	@Getter
	public static class Create {

		private String commentIdx; // 댓글의 idx
		private String postIdx; // 게시글의 idx
		private String parentCommentIdx; // 대댓글의 경우, 상위 댓글
		private String content; // 댓글 내용
		private String tagMemberIdx; // 언급당한 회원

		public Comment toEntity(Post post, Comment parentComment, Member tagMember, Member writer) {
			return Comment.builder()
					.post(post)
					.parentComment(parentComment)
					.content(content)
					.tagMember(tagMember)
					.writer(writer)
					.build();
		}


	}


}
