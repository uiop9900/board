package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import java.util.List;

public interface HashTagRepositoryCustom {

	void deleteAllHashTagsByPostIdx(Long postIdx);

	List<HashTag> getHashTagsByPostIdx(Long postIdx);

}
