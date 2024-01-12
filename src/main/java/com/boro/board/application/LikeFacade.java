package com.boro.board.application;

import com.boro.board.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeFacade {

	private final LikeService likeService;

	public Long likePost(Long postIdx) {
		return likeService.likePost(postIdx);
	}

	public Long likeComment(Long commentIdx) {
		return likeService.likeComment(commentIdx);
	}
}
