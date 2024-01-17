package com.boro.board.infrastructure.post;

import static com.boro.board.domain.post.QHashTag.hashTag;
import static com.boro.board.domain.post.QPostHashTag.postHashTag;

import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.post.HashTag;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostHashTag;
import com.boro.board.domain.post.QHashTag;
import com.boro.board.domain.post.QPostHashTag;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashTagRepositoryCustomImpl implements HashTagRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override public void deleteAllHashTagsByPostIdx(final Long postIdx) {
		queryFactory.update(hashTag)
				.set(hashTag.rowStatus, RowStatus.D)
				.where(hashTag.idx.in
						(JPAExpressions.select(postHashTag.hashTag.idx)
								.from(postHashTag)
								.where(postHashTag.post.idx.eq(postIdx)))
				)
				.execute();
	}

	@Override public void deleteHashTagsByHashTagIdxs(final List<Long> hashTagIdxs) {
		queryFactory.update(hashTag)
				.set(hashTag.rowStatus, RowStatus.D)
				.where(hashTag.idx.in(hashTagIdxs))
				.execute();
	}

	@Override public List<HashTag> getHashTagsByPostIdx(final Long postIdx) {
		return queryFactory.selectFrom(hashTag)
				.join(postHashTag).on(hashTag.idx.eq(postHashTag.hashTag.idx))
				.where(postHashTag.post.idx.eq(postIdx))
				.fetch();
	}
}
