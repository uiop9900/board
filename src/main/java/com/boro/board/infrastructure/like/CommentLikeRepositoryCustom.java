package com.boro.board.infrastructure.like;

import com.boro.board.domain.like.LikeInfo;
import java.util.List;

public interface CommentLikeRepositoryCustom {

	List<LikeInfo> getCommentsLikeCount(List<Long> commentIdxs);



}
