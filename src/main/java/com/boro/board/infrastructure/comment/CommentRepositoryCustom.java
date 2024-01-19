package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    void deleteComments(List<Long> commentIdxs);

    List<Comment> findCommentsExceptMeByPostIdx(Long postIdx, Long commentIdx);

    Comment findParentCommentByPostIdx(Long postIdx);

    Optional<Comment> findCommentRecentlyByPostIdx(Long postIdx);

    List<Comment> findCommentsSortedByPostIdx(Long postIdx);

}
