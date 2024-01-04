package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> getChildCommentByCommentIdx(Long commentIdx);

}
