package com.boro.board.infrastructure.like;

import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.PostLike;

public interface LikeStore {

	void save(PostLike postLike);

	void save(CommentLike commentLike);

	void setLikesNumber(Long idx, String redisKey, Long likesNumber);

}
