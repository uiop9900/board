package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;
import java.util.List;

public interface CommentStore {

	void save(Comment comment);

	void deleteComments(List<Long> commentIdxs);
}
