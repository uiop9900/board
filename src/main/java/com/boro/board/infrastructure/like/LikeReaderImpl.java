package com.boro.board.infrastructure.like;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.CommentLikeId;
import com.boro.board.domain.like.PostLike;
import com.boro.board.domain.like.PostLikeId;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeReaderImpl implements LikeReader {

	private final PostLikeRepository postLikeRepository;

	private final CommentLikeRepository commentLikeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override public Optional<CommentLike> getCommentLikeById(final Comment comment, final Member member) {
		return commentLikeRepository.findById(new CommentLikeId(comment.getIdx(), member.getIdx())); // 잦은 변동은 안쓰는게 나음 (캐시히트율)
	}

	@Override public Optional<PostLike> getPostLikeById(final Post post, final Member member) {
		return postLikeRepository.findById(new PostLikeId(post.getIdx(), member.getIdx()));
	}

	@Override public Long getLikeNumber(final Long idx, final String redisKey) {
		String key = redisKey + idx;
		final Object value = redisTemplate.opsForValue().get(key);
		return value == null ? 0L : (Long)value;
	}

	@Override
	public Map<Long, Long> getLikeNumbers(List<Long> idxs, String redisKey) {
		List<String> keys = idxs.stream().map(idx -> redisKey + idx).toList();
		List<Object> values = redisTemplate.opsForValue().multiGet(keys);

		HashMap<Long, Long> likes = new HashMap<>();
		for (int i = 0; i < keys.size(); i++) {
			likes.put(
					idxs.get(i),
					values.get(i) == null ? 0L : (Long) values.get(i)
			);
		}
		return likes;
	}


}
