package com.boro.board.infrastructure.like;

import java.util.List;
import java.util.Map;

public interface PostLikeRepositoryCustom {

    Map<Long, Long> getPostsLikesCount(List<Long> idxs);
}
