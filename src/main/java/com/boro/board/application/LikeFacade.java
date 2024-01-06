package com.boro.board.application;

import com.boro.board.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeFacade {

	private final LikeService likeService;

	public Long likePost(String postIdx) {
		return likeService.likePost(postIdx);
	}

	public Long likeComment(String commentIdx) {
		return likeService.likeComment(commentIdx);
	}
}
