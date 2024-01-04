package com.boro.board.interfaces;

import com.boro.board.domain.post.PostCommand.Create;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpsertPostRequest {

	@Nullable
	private String postIdx; // idx가 있으면 update, 없으면 insert
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	@Nullable
	private String memberIdx;
	@Nullable
	private String hashTags; // 해시태그 리스트 ex) #안녕 #하이 #자바 #자바스크립트

	public Create toCommand() {
		return Create.builder()
				.postIdx(postIdx)
				.title(title)
				.content(content)
				.memberIdx(memberIdx)
				.hashTags(hashTags)
				.build();
	}

}
