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
		private Long postLikes; // 해당 게시글의 좋아요 수
		private String title; // 제목
		private List<HashTagInfo.Main> hashTags; // 해시태그들
		private Integer commentCount; // 댓글 수
		private Long writerIdx;
		private String writerNickName; // 작성자
		private LocalDateTime createdAt; // 게시글 생성일


		public static Main toInfo(Post post, Long postLikes, List<HashTagInfo.Main> hashTags) {
			Member member = post.getMember();

			return Main.builder()
					.postIdx(post.getIdx())
					.title(post.getTitle())
					.postLikes(postLikes)
					.commentCount(post.getComments().size())
					.writerIdx(member.getIdx())
					.writerNickName(member.getNickName())
					.hashTags(hashTags)
					.createdAt(post.getCreatedAt())
					.build();
		}
	}


	@Builder
	@Getter
	public static class Detail {

		// post
		private Long postIdx;
		private Long postLikes; // 해당 게시글의 좋아요 수
		private String title; // 제목
		private String content; // 내용
		private Long writerIdx;
		private String writer; // 작성자
		private LocalDateTime createdAt; // 게시글 생성일
		private List<HashTagInfo.Main> hashTags; // 해시태그들
		private List<CommentInfo.Detail> comments; // 댓글들

		public static Detail toInfo(Post post, Long postLikes, List<HashTagInfo.Main> hashTags, List<CommentInfo.Detail> commentInfos) {
			Member member = post.getMember();

			return Detail.builder()
					.postIdx(post.getIdx())
					.postLikes(postLikes)
					.title(post.getTitle())
					.content(post.getContent())
					.hashTags(hashTags)
					.writerIdx(member.getIdx())
					.writer(member.getNickName())
					.createdAt(post.getCreatedAt())
					.comments(commentInfos)
					.build();
		}
	}

}
