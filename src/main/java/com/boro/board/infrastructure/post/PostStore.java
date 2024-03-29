package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import com.boro.board.domain.post.Post;
import java.util.List;

public interface PostStore {

	void savePostAndHashTags(Post post, List<String> hashTags);

	void updateHashTags(Post post, List<String> hashTags);

	void deleteHashTags(Long postIdx);

	void saveHashTags(Post post, List<String> hashTags);



}
