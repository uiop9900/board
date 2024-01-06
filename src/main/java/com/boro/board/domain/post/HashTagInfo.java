package com.boro.board.domain.post;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.like.CommentLike;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class HashTagInfo {

	@Builder
	@Getter
	public static class Main {

		private Long hashTagIdx;
		private String content;

		public static Main toInfo(HashTag hashTag) {
			return Main.builder()
					.hashTagIdx(hashTag.getIdx())
					.content(hashTag.getTagTitle())
					.build();
		}
	}

}
