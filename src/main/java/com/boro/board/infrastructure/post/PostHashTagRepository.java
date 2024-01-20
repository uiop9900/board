package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostHashTag;
import com.boro.board.domain.post.PostHashTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, PostHashTagId>, PostHashTagRepositoryCustom {

}
