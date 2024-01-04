package com.boro.board.infrastructure.post;

import com.boro.board.domain.entity.HashTag;
import com.boro.board.domain.entity.Post;
import java.util.List;

public interface PostStore {

	void savePost(Post post, List<HashTag> hashTags);

	void updatePost(Post post);



}
