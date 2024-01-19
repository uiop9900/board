package com.boro.board.domain.like;

import static com.boro.board.common.ErrorMessage.CAN_NOT_CALCULATE_LIKE;
import static com.boro.board.common.ErrorMessage.NO_LIKE_TYPE;

import com.boro.board.common.exception.LikeException;
import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.UserPrincipal;
import com.boro.board.domain.enums.LikeType;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.like.LikeReader;
import com.boro.board.infrastructure.like.LikeStore;
import com.boro.board.infrastructure.like.PostLikeRepository;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

	private final LikeReader likeReader;

	private final LikeStore likeStore;

	private final MemberReader memberReader;

	private final PostReader postReader;

	private final CommentReader commentReader;

	@Override
	@Transactional
	public void likePost(final Long postIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = postReader.getPostByIdx(postIdx);
		calculatePostLikeNumber(member, post);
	}

	@Override
	@Transactional
	public void likeComment(final Long commentIdx) {
		final Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Comment comment = commentReader.getCommentByIdx(commentIdx);
		calculateCommentLikeNumber(member, comment);
	}

	@Override public Long getLikesNumber(final Long idx, LikeType likeType) {
		if (LikeType.POST == likeType) {
			return likeReader.getPostLikesNumber(idx);
		}

		if (LikeType.COMMENT == likeType) {
			return likeReader.getCommentLikesNumber(idx);
		}
		throw new LikeException(NO_LIKE_TYPE);
	}


	// TODO: 중복로직 제거하기
	private void calculatePostLikeNumber(Member member, Post post) {
		final Optional<PostLike> previousPostLike = likeReader.getPostLikeById(post, member);

		// 기존에 좋아요 이력 미존재
		if (previousPostLike.isEmpty()) {
			PostLike postLike = PostLike.toEntity(member, post);
			likeStore.save(postLike);
		}

		// 좋아요
		final PostLike postLike = previousPostLike.get();
		if (postLike.isUnLiked()) {
			postLike.like();
		}

		// 좋아요 취소
		if (!postLike.isUnLiked()) {
			postLike.unLike();
		}

		throw new LikeException(CAN_NOT_CALCULATE_LIKE);
	}

	private void calculateCommentLikeNumber(Member member, Comment comment) {
		Optional<CommentLike> previousCommentLike = likeReader.getCommentLikeById(comment, member);

		// 기존에 좋아요 이력 미존재
		if (previousCommentLike.isEmpty()) {
			CommentLike commentLike = CommentLike.toEntity(member, comment);
			likeStore.save(commentLike);
		}

		// 좋아요
		CommentLike commentLike = previousCommentLike.get();
		if (commentLike.isUnLiked()) {
			commentLike.like();
		}

		// 좋아요 취소
		if (!commentLike.isUnLiked()) {
			commentLike.unLike();
		}

		throw new LikeException(CAN_NOT_CALCULATE_LIKE);
	}
}
