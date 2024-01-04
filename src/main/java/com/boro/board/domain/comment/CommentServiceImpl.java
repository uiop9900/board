package com.boro.board.domain.comment;

import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

	private final CommentReader commentReader;

	private final CommentStore commentStore;


	@Override
	@Transactional
	public void createComment() {

	}
}
