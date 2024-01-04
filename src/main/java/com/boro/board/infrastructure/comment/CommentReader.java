package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.Optional;

public interface CommentReader {

	Comment findCommentByIdx(Long commentIdx);

	Optional<Comment> findChildCommentByCommentIdx(Long commentIdx);

}
