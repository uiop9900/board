package com.boro.board.application;

import com.boro.board.domain.post.PostCommand;
import com.boro.board.domain.post.PostInfo;
import com.boro.board.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostFacade {

	private final PostService postService;
	public void upsertPost(PostCommand.Create create) {
		if (StringUtils.hasText(create.getPostIdx())) {
			postService.updatePost(create);
		} else {
			postService.createPost(create);
		}
	}

	public void deletePost(Long postIdx) {
		postService.deletePost(postIdx);
	}

	public PostInfo.Detail findPostDetail(Long postIdx) {
		return postService.findPostDetail(postIdx);
	}

	public List<PostInfo.Main> findPosts(Integer page, String hashTag) {
		return postService.findPosts(page, hashTag);
	}
}
