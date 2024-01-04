package com.boro.board.interfaces;

import com.boro.board.application.LikeFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.LikeBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

	private final LikeFacade likeFacade;

    /**
     * 게시글/댓글에 대한 좋아요 처리
     */
	@PostMapping
	public CommonResponse<Long> likeBoard(@RequestBody LikeBoardRequest request) {
		return null;
	}

}
