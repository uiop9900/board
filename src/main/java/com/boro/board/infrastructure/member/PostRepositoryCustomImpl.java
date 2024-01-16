package com.boro.board.infrastructure.member;

import com.boro.board.domain.post.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.boro.board.domain.post.QPost.post;

@Component
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getPostsByHashTag(String hashTag, Pageable pageable) {
//        List<Post> posts = jpaQueryFactory.select(
//                        QHashTag.hashTag
//                    )
//                .from(QHashTag.hashTag)
//                .where(QHashTag.hashTag.tagTitle.eq(hashTag))
//                .limit(pageable.getPageSize())  // 페이지 크기 설정
//                .offset(pageable.getOffset())  // 오프셋 설정
//                .fetch();
//
//        return new PageImpl<>(posts, pageable, posts.size());
        return null;
    }
}
