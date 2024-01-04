package com.boro.board.domain.comment;

import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.post.PostReader;
import com.sun.security.auth.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

	private final CommentReader commentReader;

	private final CommentStore commentStore;

	private final PostReader postReader;

	@Override
	@Transactional
	public void createComment(final Create create) {
		final Post post = postReader.findPostByIdx(Long.parseLong(create.getPostIdx()));




	}
}
