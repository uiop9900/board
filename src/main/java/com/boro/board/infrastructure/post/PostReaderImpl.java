package com.boro.board.infrastructure.post;

import com.boro.board.domain.entity.Post;
import com.boro.board.domain.exception.MemberException;
import com.boro.board.infrastructure.member.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

	private final PostRepository postRepository;

	@Override public Post findPostByIdx(final Long idx) {
		return postRepository.findById(idx).orElseThrow(() -> new MemberException("dd"));
	}
}
