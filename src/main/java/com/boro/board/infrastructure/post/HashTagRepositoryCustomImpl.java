package com.boro.board.infrastructure.post;

import static com.boro.board.domain.entity.QHashTag.hashTag;

import com.boro.board.domain.entity.RowStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashTagRepositoryCustomImpl implements HashTagRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override public void deleteAllHashTagsByPostIdx(final Long postIdx) {
		queryFactory.update(hashTag)
				.set(hashTag.rowStatus, RowStatus.D)
				.where(hashTag.post.idx.eq(postIdx))
				.execute();
	}
}
