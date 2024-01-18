package com.boro.board.domain.like;

import com.boro.board.domain.enums.LikeType;
import org.springframework.transaction.annotation.Transactional;

public interface LikeService {

	@Transactional Long likePost(Long poseIdx);

	@Transactional Long likeComment(Long commentIdx);

	Long getLikesNumber(Long idx, LikeType likeType);

}
