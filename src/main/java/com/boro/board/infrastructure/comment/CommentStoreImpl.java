package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentStoreImpl implements CommentStore {

	private final CommentRepository commentRepository;

	@Override
	@Transactional
	public void save(final Comment comment) {
		commentRepository.save(comment);
	}


}
