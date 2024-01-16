package com.boro.board.domain.post;

import com.boro.board.domain.comment.CommentInfo;
import com.boro.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class PostInfo {


	@Builder
	@Getter
	public static class Main {

		private Long postIdx;
		private String title; // 제목
		private List<HashTagInfo.Main> hashTags; // 해시태그들
		private Long postLikes; // 해당 게시글의 좋아요 수
		private Integer commentCount; // 댓글 수
		private Long writerIdx;
		private String nickName; // 작성자
		private LocalDateTime createdAt; // 게시글 생성일


		public static Main toInfo(Post post, Long postLikes) {
			Member member = post.getMember();

			return Main.builder()
					.postIdx(post.getIdx())
					.title(post.getTitle())
					.postLikes(postLikes)
					.commentCount(post.getComments().size())
					.writerIdx(member.getIdx())
					.nickName(member.getNickName())
					.createdAt(post.getCreatedAt())
					.build();
		}
	}


	@Builder
	@Getter
	public static class Detail {

		private Long postIdx;
		private String title; // 제목
		private String content; // 내용
		private List<HashTagInfo.Main> hashTags; // 해시태그들
		private Long postLikes; // 해당 게시글의 좋아요 수
		private List<CommentInfo.Detail> comments; // 댓글들
		private Long writerIdx;
		private String nickName; // 작성자
		private LocalDateTime createdAt; // 게시글 생성일


		public static Detail toInfo(Post post, List<CommentInfo.Detail> commentInfos, Long postLikes) {
			Member member = post.getMember();


			return Detail.builder()
					.postIdx(post.getIdx())
					.title(post.getTitle())
					.content(post.getContent())
					.postLikes(postLikes)
					.comments(commentInfos)
					.writerIdx(member.getIdx())
					.nickName(member.getNickName())
					.createdAt(post.getCreatedAt())
					.build();
		}
	}

}
