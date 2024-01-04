package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.Post;

public interface PostReader {

	Post findPostByIdx(Long idx);

}
