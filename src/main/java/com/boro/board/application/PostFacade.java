package com.boro.board.application;

import com.boro.board.domain.post.PostCommand;
import com.boro.board.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class PostFacade {

	private final PostService postService;
	public void upsertPost(PostCommand.Create create) {
		if (StringUtils.hasText(create.getPostIdx())) {
			// update
			postService.updatePost(create);
		}

		// insert
		postService.createPost(create);
	}


}
