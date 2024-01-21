package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentReader {

	Comment getCommentByIdx(Long commentIdx);

	Optional<Comment> getCommentByParentCommentIdx(Long commentIdx);

	List<Comment> findCommentsExceptMeByPostIdx(Long postIdx, Long commentIdx);

	Comment findParentCommentByPostIdx(Long postIdx);

	List<Comment> findCommentsByTagMemberIdx(Long memberIdx);

	Optional<Comment> findCommentRecentlyByPostIdx(Long postIdx);

	List<Comment> findCommentsSortedByPostIdx(Long postIdx);

}
