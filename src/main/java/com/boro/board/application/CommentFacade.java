package com.boro.board.application;

import com.boro.board.domain.comment.CommentCommand;
import com.boro.board.domain.comment.CommentService;
import com.boro.board.domain.member.MemberService;
import com.boro.board.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {

	private final CommentService commentService;

	private final PostService postService;

	private final MemberService memberService;

	public void upsertComment(CommentCommand.Create create) {
		commentService.createComment(create);
	}

}
