package com.boro.board.infrastructure.like;

import static com.boro.board.domain.like.QCommentLike.commentLike;

import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.like.LikeInfo;
import com.boro.board.domain.like.QCommentLike;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeRepositoryCustomImpl implements CommentLikeRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;


	@Override public Map<Long, Long> getCommentsLikeCount(final List<Long> commentIdxs) {
		final List<Tuple> result = jpaQueryFactory
				.select(
						commentLike.comment.idx,
						commentLike.count())
				.from(commentLike)
				.where(
						commentLike.comment.idx.in(commentIdxs)
								.and(commentLike.rowStatus.eq(RowStatus.U))
				)
				.groupBy(commentLike)
				.fetch();

		return result.stream()
				.collect(Collectors.toMap(
						tuple -> tuple.get(commentLike.comment.idx),
						tuple -> tuple.get(commentLike.count())
				));

	}
}
