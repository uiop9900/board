package com.boro.board.infrastructure.like;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.CommentLikeId;
import com.boro.board.domain.like.PostLike;
import com.boro.board.domain.like.PostLikeId;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeReaderImpl implements LikeReader {

	private final PostLikeRepository postLikeRepository;

	private final CommentLikeRepository commentLikeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override public Optional<CommentLike> getCommentLikeById(final Comment comment, final Member member) {
		return commentLikeRepository.findById(new CommentLikeId(comment, member));
	}

	@Override public Optional<PostLike> getPostLikeById(final Post post, final Member member) {
		return postLikeRepository.findById(new PostLikeId(post, member));
	}

	@Override public Long findLikes(final Long idx, final String redisKey) {
		String key = redisKey + idx;
		final Object value = redisTemplate.opsForValue().get(key);
		return value == null ? 0L : (Long)value;
	}


}
