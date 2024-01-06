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
import com.boro.board.interfaces.dtos.UserPrincipal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.boro.board.common.ErrorMessage.CAN_NOT_CALCULATE_LIKE;

@Component
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final LikeReader likeReader;

	private final LikeStore likeStore;

	private final MemberReader memberReader;

	private final PostReader postReader;

	private final CommentReader commentReader;

	private final static String POST_LIKE_REDIS_KEY = "BOARD::LIKE::POST::";

	private final static String COMMENT_LIKE_REDIS_KEY = "BOARD::LIKE::COMMENT::";

	@Override
	@Transactional public Long likePost(final String postIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = postReader.findPostByIdx(Long.parseLong(postIdx));


		Long previousLikeNumber = likeReader.findLikes(post.getIdx(), POST_LIKE_REDIS_KEY);
		Long resultLike = calculateLikeNumber(member, post, previousLikeNumber);

		likeStore.setLikesNumber(post.getIdx(), POST_LIKE_REDIS_KEY, resultLike);
		return resultLike;
	}

	@Override public Long likeComment(final String commentIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Comment comment = commentReader.findCommentByIdx(Long.parseLong(commentIdx));

		final CommentLike commentLike = CommentLike.of(comment, member);
		likeStore.save(commentLike);
		return null;
	}

	private Long calculateLikeNumber(Member member, Post post, Long likeNumber) {
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
}
