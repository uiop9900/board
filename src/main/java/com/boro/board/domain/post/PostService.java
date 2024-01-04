package com.boro.board.domain.post;

import com.boro.board.domain.post.PostCommand.Create;

public interface PostService {

	void createPost(Create create);
	void updatePost(Create create);
}
