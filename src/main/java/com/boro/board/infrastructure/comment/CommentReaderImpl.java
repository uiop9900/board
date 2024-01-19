package com.boro.board.infrastructure.comment;

import static com.boro.board.common.ErrorMessage.NOT_FOUND_COMMENT;

import com.boro.board.domain.comment.Comment;
import com.boro.board.common.exception.CommentException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

	private final CommentRepository commentRepository;

	@Override public Comment getCommentByIdx(final Long commentIdx) {
		return commentRepository.findById(commentIdx).orElseThrow(() -> new CommentException(NOT_FOUND_COMMENT));
	}

	@Override public List<Comment> findCommentsExceptMeByPostIdx(final Long postIdx, final Long commentIdx) {
		return commentRepository.findCommentsExceptMeByPostIdx(postIdx, commentIdx);
	}

	@Override public Comment findParentCommentByPostIdx(final Long postIdx) {
		return commentRepository.findParentCommentByPostIdx(postIdx);
	}

	@Override
	public List<Comment> findCommentsByTagMemberIdx(Long memberIdx) {
		return commentRepository.findCommentsByTagMemberIdx(memberIdx);
	}

	@Override
	public Optional<Comment> findCommentRecentlyByPostIdx(Long postIdx) {
		return commentRepository.findCommentRecentlyByPostIdx(postIdx);
	}

	@Override public List<Comment> findCommentsSortedByPostIdx(final Long postIdx) {
		return commentRepository.findCommentsSortedByPostIdx(postIdx);
	}
}
