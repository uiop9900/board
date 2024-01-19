package com.boro.board.domain.like;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeInfo {

		Map<Long, Long> likeInfos; // commentIdx, likeCount

}
