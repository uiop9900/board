package com.boro.board.domain.comment;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

public class CommentCommand {

	@Builder
	@Getter
	public static class Create {

		private String postIdx; // idx가 있으면 update, 없으면 insert
		private String title;
		private String content;
		private String memberIdx;
		private String hashTags; // 해시태그 리스트 ex) #안녕 #하이 #자바 #자바스크립트

		public Post toEntity(Member member) {
			return Post.builder()
					.title(title)
					.content(content)
					.member(member)
					.build();
		}


	}


}
