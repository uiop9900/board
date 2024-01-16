package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long>, HashTagRepositoryCustom {

	Optional<HashTag> findHashTagByTagTitle(String hashTag);

}
