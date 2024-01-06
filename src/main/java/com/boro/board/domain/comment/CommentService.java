package com.boro.board.domain.comment;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

	@Transactional void createComment(CommentCommand.Create create);

	@Transactional void updateComment(CommentCommand.Update update);

	@Transactional void deleteComment(String commentIdx);

	List<CommentInfo.Main> getCommentsMentioned();

}
