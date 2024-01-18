package com.boro.board.infrastructure.like;

import static com.boro.board.domain.like.QCommentLike.commentLike;

import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.like.LikeInfo;
import com.boro.board.domain.like.QCommentLike;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeRepositoryCustomImpl implements CommentLikeRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;


	@Override public List<LikeInfo> getCommentsLikeCount(final List<Long> commentIdxs) {
		return jpaQueryFactory
				.select(Projections.constructor(LikeInfo.class,
						commentLike.comment.idx,
						commentLike.count()))
				.from(commentLike)
				.where(
						commentLike.comment.idx.in(commentIdxs)
								.and(commentLike.rowStatus.eq(RowStatus.U))
				)
				.groupBy(commentLike)
				.fetch();
	}
}
