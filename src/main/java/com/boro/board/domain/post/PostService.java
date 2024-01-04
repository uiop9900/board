package com.boro.board.domain.post;

import com.boro.board.domain.post.PostCommand.Create;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {

	@Transactional void createPost(Create create);
	@Transactional void updatePost(Create create);

	@Transactional void deletePost(Long postIdx);
}
