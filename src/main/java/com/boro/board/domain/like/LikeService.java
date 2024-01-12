package com.boro.board.domain.like;

import org.springframework.transaction.annotation.Transactional;

public interface LikeService {

	@Transactional Long likePost(Long poseIdx);

	@Transactional Long likeComment(Long commentIdx);

}
