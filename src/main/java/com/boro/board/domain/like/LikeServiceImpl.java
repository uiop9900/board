package com.boro.board.domain.like;

import com.boro.board.common.exception.LikeException;
import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.like.LikeReader;
import com.boro.board.infrastructure.like.LikeStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.domain.entity.UserPrincipal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.boro.board.common.ErrorMessage.CAN_NOT_CALCULATE_LIKE;
import static com.boro.board.common.property.RedisKeyProperties.COMMENT_LIKE_REDIS_KEY;
import static com.boro.board.common.property.RedisKeyProperties.POST_LIKE_REDIS_KEY;


@Component
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final LikeReader likeReader;

	private final LikeStore likeStore;

	private final MemberReader memberReader;

	private final PostReader postReader;

	private final CommentReader commentReader;

	@Override
	@Transactional
	public Long likePost(final Long postIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = postReader.getPostByIdx(postIdx);

		Long previousLikeNumber = likeReader.getLikeNumber(post.getIdx(), POST_LIKE_REDIS_KEY);
		Long resultLike = calculatePostLikeNumber(member, post, previousLikeNumber);

		likeStore.setLikesNumber(post.getIdx(), POST_LIKE_REDIS_KEY, resultLike);
		return resultLike;
	}

	@Override
	@Transactional
	public Long likeComment(final Long commentIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Comment comment = commentReader.getCommentByIdx(commentIdx);

		Long previousLikeNumber = likeReader.getLikeNumber(comment.getIdx(), COMMENT_LIKE_REDIS_KEY);
		Long resultLike = calculateCommentLikeNumber(member, comment, previousLikeNumber);

		likeStore.setLikesNumber(comment.getIdx(), COMMENT_LIKE_REDIS_KEY, resultLike);
		return resultLike;
	}


	// TODO: 중복로직 제거하기
	private Long calculatePostLikeNumber(Member member, Post post, Long likeNumber) {
		final Optional<PostLike> previousPostLike = likeReader.getPostLikeById(post, member);

		// 기존에 좋아요 이력 미존재
		if (previousPostLike.isEmpty()) {
			PostLike postLike = PostLike.toEntity(member, post);
			likeStore.save(postLike);
			likeNumber += 1L;
			return likeNumber;
		}

		// 좋아요
		final PostLike postLike = previousPostLike.get();
		if (postLike.isUnLiked()) {
			postLike.like();
			likeNumber += 1L;
			return likeNumber;
		}

		// 좋아요 취소
		if (!postLike.isUnLiked()) {
			postLike.unLike();
			likeNumber -= 1L;
			return likeNumber;
		}

		throw new LikeException(CAN_NOT_CALCULATE_LIKE);
	}

	private Long calculateCommentLikeNumber(Member member, Comment comment, Long likeNumber) {
		Optional<CommentLike> previousCommentLike = likeReader.getCommentLikeById(comment, member);

		// 기존에 좋아요 이력 미존재
		if (previousCommentLike.isEmpty()) {
			CommentLike commentLike = CommentLike.toEntity(member, comment);
			likeStore.save(commentLike);
			likeNumber += 1L;
			return likeNumber;
		}

		// 좋아요
		CommentLike commentLike = previousCommentLike.get();
		if (commentLike.isUnLiked()) {
			commentLike.like();
			likeNumber += 1L;
			return likeNumber;
		}

		// 좋아요 취소
		if (!commentLike.isUnLiked()) {
			commentLike.unLike();
			likeNumber -= 1L;
			return likeNumber;
		}

		throw new LikeException(CAN_NOT_CALCULATE_LIKE);
	}
}
