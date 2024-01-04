package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long>, HashTagRepositoryCustom {

}
