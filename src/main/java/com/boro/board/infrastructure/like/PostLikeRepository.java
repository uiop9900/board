package com.boro.board.infrastructure.like;

import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.like.PostLike;
import com.boro.board.domain.like.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

	Long countPostLikeByPostIdxAndRowStatus(Long postIdx, RowStatus rowStatus);

}
