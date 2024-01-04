package com.boro.board.infrastructure.post;

import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_POST;

import com.boro.board.domain.entity.Post;
import com.boro.board.domain.exception.PostException;
import com.boro.board.infrastructure.member.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

	private final PostRepository postRepository;

	@Override public Post findPostByIdx(final Long idx) {
		return postRepository.findById(idx).orElseThrow(() -> new PostException(NOT_FOUND_POST));
	}
}
