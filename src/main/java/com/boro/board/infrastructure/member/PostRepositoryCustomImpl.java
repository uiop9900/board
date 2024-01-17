package com.boro.board.infrastructure.member;

import static com.boro.board.domain.post.QPostHashTag.postHashTag;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.QHashTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getPostsByHashTag(String hashTag, Pageable pageable) {
        List<Post> posts = jpaQueryFactory.selectDistinct(postHashTag.post)
                .from(QHashTag.hashTag)
                .join(postHashTag).on(QHashTag.hashTag.idx.eq(postHashTag.hashTag.idx))
                .where(
                    hashTagEq(hashTag)
                )
                .limit(pageable.getPageSize())  // 페이지 크기 설정
                .offset(pageable.getOffset())  // 오프셋 설정
                .fetch();

        return new PageImpl<>(posts, pageable, posts.size());
//        쿼리>
//        select post_idx from hashtag
//        join post_hashtag on hashtag.hash_tag_idx = post_hashtag.hash_tag_idx
//        where tag_title = 'tag title';
    }

    private BooleanExpression hashTagEq(String hashTag) {
        if (StringUtils.isEmpty(hashTag)) {
            return null;
        }
        return QHashTag.hashTag.tagTitle.eq(hashTag);
    }
}
