package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReader {

	Post getPostByIdx(Long idx);

	Page<Post> findPostsByHashTag(String hashTag, Pageable pageable);

}
