package com.boro.board.domain.like;

import com.boro.board.domain.enums.LikeType;
import org.springframework.transaction.annotation.Transactional;

public interface LikeService {

	@Transactional void likePost(Long poseIdx);

	@Transactional void likeComment(Long commentIdx);

	Long getLikesNumber(Long idx, LikeType likeType);

}
