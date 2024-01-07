package com.boro.board.domain.comment;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

public class CommentCommand {

	@Builder
	@Getter
	public static class Create {

		private String postIdx; // 게시글의 idx
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


	@Builder
	@Getter
	public static class Update {
		private String commentIdx;
		private String content;
		private String tagMemberIdx;
	}
}
