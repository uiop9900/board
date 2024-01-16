package com.boro.board.domain.post;

import com.boro.board.domain.member.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class HashTagCommand {

	@Builder
	@Getter
	public static class Create {

		private List<String> tagTitles;

		public List<HashTag> toEntities(Post post) {

			List<HashTag> hashTags = new ArrayList<>();
			for (String tagTitle : tagTitles) {
				final HashTag hashTag = HashTag.builder()
//						.post(post)
						.tagTitle(tagTitle)
						.build();
				hashTags.add(hashTag);
			}
			return hashTags;
		}
	}

	@Builder
	@Getter
	public static class Update {

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
