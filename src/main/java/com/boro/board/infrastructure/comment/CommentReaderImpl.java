package com.boro.board.infrastructure.comment;

import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_COMMENT;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.exception.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

	private final CommentRepository commentRepository;

	@Override public Comment findCommentByIdx(final Long commentIdx) {
		return commentRepository.findById(commentIdx).orElseThrow(() -> new PostException(NOT_FOUND_COMMENT));
	}
}
