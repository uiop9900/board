package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentReader {

	Comment findCommentByIdx(Long commentIdx);

	List<Comment> getCommentsExceptMeByPostIdx(Long postIdx, Long commentIdx);

	Comment getParentCommentByPostIdx(Long postIdx);

	List<Comment> getCommentsByTagMemberIdx(Long memberIdx);

	Optional<Comment> getCommentRecentlyByPostIdx(Long postIdx);



}
