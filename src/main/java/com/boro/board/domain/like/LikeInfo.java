package com.boro.board.domain.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeInfo {

	private Long commentIdx;
	private Long likeCount;

}
