package com.boro.board.infrastructure.like;

import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeStoreImpl implements LikeStore {

	private final PostLikeRepository postLikeRepository;

	private final CommentLikeRepository commentLikeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void save(final PostLike postLike) {
		postLikeRepository.save(postLike);
	}

	@Override
	public void save(final CommentLike commentLike) {
		commentLikeRepository.save(commentLike);
	}

	@Override public void setLikesNumber(final Long idx, final String redisKey, Long likesNumber) {
		String key = redisKey + idx;
		redisTemplate.opsForValue().set(key, likesNumber);
	}

}
