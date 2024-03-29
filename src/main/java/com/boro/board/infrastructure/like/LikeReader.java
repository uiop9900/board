package com.boro.board.infrastructure.like;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.LikeInfo;
import com.boro.board.domain.like.PostLike;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikeReader {

	Long getPostLikesNumber(Long postIdx);

	Map<Long, Long> getPostsLikesNumber(List<Long> postIdxs);

	Long getCommentLikesNumber(Long commentIdx);

	Map<Long, Long> getCommentsLikesNumber(List<Long> commentIdx);

	Optional<CommentLike> getCommentLikeById(final Comment comment, final Member member);

	Optional<PostLike> getPostLikeById(final Post post, final Member member);


}
