package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import java.util.List;

public interface HashTagRepositoryCustom {

	void deleteAllHashTagsByPostIdx(Long postIdx);

	void deleteHashTagsByHashTagIdxs(List<Long> hashTagIdxs);

	List<HashTag> getHashTagsByPostIdx(Long postIdx);


}
