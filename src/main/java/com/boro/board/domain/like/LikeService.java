package com.boro.board.domain.like;

import org.springframework.transaction.annotation.Transactional;

public interface LikeService {

	@Transactional Long likePost(String poseIdx);

	@Transactional Long likeComment(String commentIdx);

}
