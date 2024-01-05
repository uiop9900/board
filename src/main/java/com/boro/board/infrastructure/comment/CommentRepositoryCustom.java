package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> getChildCommentByCommentIdx(Long commentIdx);

    void deleteComments(List<Long> commentIdxs);

    List<Comment> getCommentsByPostIdx(Long postIdx);

    List<Comment> getCommentsExceptMeByPostIdx(Long postIdx, Long commentIdx);

    Comment getParentCommentByPostIdx(Long postIdx);

}
