package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReader {

	Post findPostByIdx(Long idx);

	Page<Post> getPosts(Pageable pageable);

	Page<Post> getPostsByHashTag(String hashTag, Pageable pageable);

}
