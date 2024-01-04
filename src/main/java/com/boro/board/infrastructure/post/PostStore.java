package com.boro.board.infrastructure.post;

import com.boro.board.domain.entity.HashTag;
import com.boro.board.domain.entity.Post;
import java.util.List;

public interface PostStore {

	void savePostAndHashTags(Post post, List<HashTag> hashTags);

	void updateHashTags(Post post, List<HashTag> hashTags);

	void deleteHashTags(Long postIdx);

	void saveHashTags(Post post, List<HashTag> hashTags);



}
