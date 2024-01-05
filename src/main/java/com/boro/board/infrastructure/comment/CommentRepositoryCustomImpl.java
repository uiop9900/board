package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.comment.QComment;
import com.boro.board.domain.entity.RowStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.boro.board.domain.comment.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Comment> getChildCommentByCommentIdx(Long commentIdx) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(comment)
                .where(
                        comment.parentComment.idx.eq(commentIdx)
                                .and(comment.rowStatus.eq(RowStatus.U)) // 대댓글이 존재하는 경우
                ).fetchFirst()
        );
    }

    @Override
    public void deleteComments(final List<Long> commentIdxs) {
        jpaQueryFactory.delete(comment)
            .where(comment.idx.in(commentIdxs))
            .execute();
    }

    @Override
    public List<Comment> getCommentsByPostIdx(final Long postIdx) {
        return jpaQueryFactory.selectFrom(comment)
            .where(
                comment.post.idx.eq(postIdx)
                    .and(comment.rowStatus.eq(RowStatus.U))
            )

            .fetch();
    }

    @Override public List<Comment> getCommentsExceptMeByPostIdx(final Long postIdx, Long commentIdx) {
        return jpaQueryFactory.selectFrom(comment)
            .where(
                comment.post.idx.eq(postIdx)
                    .and(comment.rowStatus.eq(RowStatus.U))
                    .and(comment.idx.ne(commentIdx)) // 현재 내 댓글을 제외하고 조회한다.
            )
            .fetch();
    }

    @Override public Comment getParentCommentByPostIdx(final Long postIdx) {
        return jpaQueryFactory.selectFrom(comment)
            .where(
                comment.post.idx.eq(postIdx)
                    .and(comment.parentComment.isNull()) // 최초의 댓글 조회
            ).fetchFirst();
    }

}
