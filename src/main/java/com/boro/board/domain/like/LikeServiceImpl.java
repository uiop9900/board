package com.boro.board.domain.like;

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
		// 좋아요 누른 사람
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = postReader.findPostByIdx(Long.parseLong(postIdx));

		final Optional<PostLike> previousPostLike = likeReader.getPostLikeById(post, member);


		Long likesNumber = likeReader.findLikes(post.getIdx(), POST_LIKE_REDIS_KEY);

		// 기존에 좋아요 하지 않았으면,
		if (previousPostLike.isEmpty()) {
			final PostLike postLike = PostLike.of(post, member);
			// 기존에 좋아요 했으면 취소고, 아니면 좋아요임.
			likeStore.save(postLike);
			likesNumber += 1L;
			likeStore.setLikesNumber(post.getIdx(), POST_LIKE_REDIS_KEY, likesNumber);

			return likesNumber;
		}

		final PostLike postLike = previousPostLike.get();
		// 좋아요
		if (postLike.isUnLiked()) {
			postLike.like();
			likesNumber += 1L;
		}

		// 좋아요 취소
		if (!postLike.isUnLiked()) {
			postLike.unLike();
			likesNumber -= 1L;
		}

		likeStore.setLikesNumber(post.getIdx(), POST_LIKE_REDIS_KEY, likesNumber);

		return likesNumber;
	}

	@Override public Long likeComment(final String commentIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Comment comment = commentReader.findCommentByIdx(Long.parseLong(commentIdx));

		final CommentLike commentLike = CommentLike.of(comment, member);
		likeStore.save(commentLike);
		return null;
	}
}
