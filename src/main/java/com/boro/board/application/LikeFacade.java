package com.boro.board.application;

import com.boro.board.domain.enums.LikeType;
import com.boro.board.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeFacade {

	private final LikeService likeService;

	public Long likePost(Long postIdx) {
		likeService.likePost(postIdx);
		return likeService.getLikesNumber(postIdx, LikeType.POST);
	}

	public Long likeComment(Long commentIdx) {
		likeService.likeComment(commentIdx);
		return likeService.getLikesNumber(commentIdx, LikeType.COMMENT);
	}
}
