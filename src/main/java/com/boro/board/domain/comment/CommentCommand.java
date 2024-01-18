package com.boro.board.domain.comment;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

public class CommentCommand {

	@Builder
	@Getter
	public static class Create {

		private Long postIdx; // 게시글의 idx
		private Long parentCommentIdx; // 부모댓글 idx
		private String content; // 댓글 내용
		private Long tagMemberIdx; // 언급당한 회원

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


	@Builder
	@Getter
	public static class Update {
		private Long commentIdx;
		private String content;
		private Long tagMemberIdx;
	}
}
