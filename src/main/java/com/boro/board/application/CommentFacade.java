package com.boro.board.application;

import com.boro.board.domain.comment.CommentCommand;
import com.boro.board.domain.comment.CommentInfo;
import com.boro.board.domain.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {

	private final CommentService commentService;

	public void createComment(CommentCommand.Create create) {
		commentService.createComment(create);
	}

	public void updateComment(CommentCommand.Update update) {
		commentService.updateComment(update);
	}

	public void deleteComment(String commentIdx) {
		commentService.deleteComment(commentIdx);
	}

	public List<CommentInfo.Main> getCommentsMentioned() {
		return commentService.getCommentsMentioned();
	}

}
