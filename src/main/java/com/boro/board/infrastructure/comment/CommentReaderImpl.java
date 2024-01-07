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

	@Override public Comment findCommentByIdx(final Long commentIdx) {
		return commentRepository.findById(commentIdx).orElseThrow(() -> new CommentException(NOT_FOUND_COMMENT));
	}

	@Override public List<Comment> getCommentsExceptMeByPostIdx(final Long postIdx, final Long commentIdx) {
		return commentRepository.getCommentsExceptMeByPostIdx(postIdx, commentIdx);
	}

	@Override public Comment getParentCommentByPostIdx(final Long postIdx) {
		return commentRepository.getParentCommentByPostIdx(postIdx);
	}

	@Override
	public List<Comment> getCommentsByTagMemberIdx(Long memberIdx) {
		return commentRepository.getCommentsByTagMemberIdx(memberIdx);
	}

	@Override
	public Optional<Comment> getCommentRecentlyByPostIdx(Long postIdx) {
		return commentRepository.getCommentRecentlyByPostIdx(postIdx);
	}
}
