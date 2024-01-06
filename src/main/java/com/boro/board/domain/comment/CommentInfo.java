package com.boro.board.domain.comment;

import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentInfo {

	@Builder
	@Getter
	public static class Main {

		private Long tagMemberIdx; // 태그당한 member의 idx
		private String nickName;
		private String content; // 댓글 내용
		private Long postIdx; // 댓글의 게시글
		private LocalDateTime createdAt;

		public static Main toInfo(Comment comment) {
			Member mentionedMember = comment.getTagMember();
			return Main.builder()
					.tagMemberIdx(mentionedMember.getIdx())
					.nickName(mentionedMember.getNickName())
					.content(comment.getContent())
					.postIdx(comment.getPost().getIdx())
					.createdAt(comment.getCreatedAt())
					.build();
		}
	}

}
