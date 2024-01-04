package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;

public interface CommentStore {

	void save(Comment comment);

}
