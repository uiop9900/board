package com.boro.board.application;

import com.boro.board.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeFacade {

	private final LikeService likeService;

	public Long likePost(String postIdx) {
		// db가 먼저 쌓이고, 숫자가 올라간다. 둘 중 하나만 되서는 안되고 같이 되어야 한다-> 한 트랜잭션에 있어야 한다.
		return likeService.likePost(postIdx);
	}

	public Long likeComment(String commentIdx) {
		likeService.likeComment(commentIdx);
		return null;
	}
}
