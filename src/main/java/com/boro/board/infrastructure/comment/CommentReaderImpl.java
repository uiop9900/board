package com.boro.board.infrastructure.comment;

import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_COMMENT;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

	private final CommentRepository commentRepository;

	@Override public Comment findCommentByIdx(final Long commentIdx) {
		return commentRepository.findById(commentIdx).orElseThrow(() -> new CommentException(NOT_FOUND_COMMENT));
	}

	@Override
	public Optional<Comment> findChildCommentByCommentIdx(Long commentIdx) {
		return commentRepository.getChildCommentByCommentIdx(commentIdx);
	}
}
