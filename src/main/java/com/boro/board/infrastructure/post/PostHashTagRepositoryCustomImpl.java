package com.boro.board.infrastructure.post;

import com.boro.board.domain.enums.RowStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.boro.board.domain.post.QPostHashTag.postHashTag;

@Component
@RequiredArgsConstructor
public class PostHashTagRepositoryCustomImpl implements PostHashTagRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deletePostHashTagByPostIdx(Long postIdx) {
        jpaQueryFactory.update(postHashTag)
            .set(postHashTag.rowStatus, RowStatus.D)
            .where(postHashTag.post.idx.eq(postIdx))
            .execute();


    }
}
