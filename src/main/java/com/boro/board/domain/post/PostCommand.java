package com.boro.board.domain.post;

import com.boro.board.domain.entity.HashTag;
import com.boro.board.domain.entity.Post;
import com.boro.board.domain.post.HashTagCommand.Create;
import com.boro.board.infrastructure.member.Member;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

public class PostCommand {

	@Builder
	@Getter
	public static class Create {

		private String postIdx; // idx가 있으면 update, 없으면 insert
		private String title;
		private String content;
		private String memberIdx;
		private String hashTags; // 해시태그 리스트 ex) #안녕 #하이 #자바 #자바스크립트

		public Post toEntity(Optional<Member> member) {
			return Post.builder()
					.title(title)
					.content(content)
					.member(member.isPresent() ? member.get() : null)
					.build();
		}

		public List<HashTag> toHashTagCommand() {
			final List<String> tagTitles = Arrays.stream(hashTags.split("#"))
					.map(hashtag -> hashtag.trim())
					.filter(hashtag -> !hashtag.isEmpty()) // 빈 문자열 제외
					.distinct()
					.collect(Collectors.toList());

			List<HashTag> list = new ArrayList<>();
			for (String tagTitle : tagTitles) {
				final HashTag hashTag = HashTag.builder()
						.tagTitle(tagTitle)
						.build();
				list.add(hashTag);
			}
			return list;
		}
	}

	@Builder
	@Getter
	public static class Update {

		private String postIdx; // idx가 있으면 update, 없으면 insert
		private String title;
		private String content;
		private Member member;
		private List<HashTag> hashTags; // 해시태그 리스트 ex) #안녕 #하이 #자바 #자바스크립트

	}


}
