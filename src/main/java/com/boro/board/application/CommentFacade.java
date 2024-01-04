package com.boro.board.application;

import com.boro.board.domain.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {

	private final CommentService commentService;

	public void upsertComment() {

	}

}
