package com.boro.board.infrastructure.like;

import com.boro.board.domain.like.LikeInfo;
import java.util.List;
import java.util.Map;

public interface CommentLikeRepositoryCustom {

	Map<Long, Long> getCommentsLikeCount(List<Long> commentIdxs);



}
