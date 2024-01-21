package com.boro.board.infrastructure.like;

import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.like.QPostLike;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.boro.board.domain.like.QCommentLike.commentLike;
import static com.boro.board.domain.like.QPostLike.postLike;

@Component
@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Map<Long, Long> getPostsLikesCount(List<Long> idxs) {
        final List<Tuple> result = jpaQueryFactory
                .select(
                        postLike.post.idx,
                        postLike.count())
                .from(postLike)
                .where(
                        postLike.post.idx.in(idxs)
                                .and(postLike.rowStatus.eq(RowStatus.U))
                )
                .groupBy(postLike)
                .fetch();

        return result.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(postLike.post.idx),
                        tuple -> tuple.get(postLike.count())
                ));
    }
}
