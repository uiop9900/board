package com.boro.board.infrastructure.like;

import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.like.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId>, CommentLikeRepositoryCustom {

	Long countByCommentIdx(Long commentIdx);

}
