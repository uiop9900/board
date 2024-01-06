package com.boro.board.infrastructure.member;

import com.boro.board.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getPostsByHashTag(String hashTag, Pageable pageable);

}
