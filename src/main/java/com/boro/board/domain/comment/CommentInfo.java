package com.boro.board.domain.comment;

import com.boro.board.domain.member.Member;
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

	@Builder
	@Getter
	public static class Detail {

		private Long tagMemberIdx; // 태그당한 member의 idx
		private String tagMemberNickName; // 댓글에 태그당한 멤버닉네임
		private String content; // 댓글 내용
		private Long commentLikes; // 댓글 좋아요 수
		private String writerNickName; // 작성자
		private LocalDateTime createdAt;

		public static Detail toInfo(Comment comment, Long commentLikes) {
			Member mentionedMember = comment.getTagMember();
			return Detail.builder()
					.tagMemberIdx(mentionedMember == null ? null : mentionedMember.getIdx())
					.tagMemberNickName(mentionedMember == null ? null : mentionedMember.getNickName())
					.content(comment.getContent())
					.commentLikes(commentLikes)
					.createdAt(comment.getCreatedAt())
					.build();
		}
	}
}
