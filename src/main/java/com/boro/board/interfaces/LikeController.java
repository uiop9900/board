package com.boro.board.interfaces;

import com.boro.board.application.LikeFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.LikePostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
   * 게시글에 대한 좋아요 처리
   */
	@PostMapping("/post")
	public CommonResponse<Long> likePost(@RequestBody @Valid LikePostRequest request) {
		return CommonResponse.success(likeFacade.likePost(request.getPostIdx()));
	}


	/**
	 * 댓글에 대한 좋아요 처리
	 */
	@PostMapping("/comment")
	@Cacheable
	public CommonResponse<Long> likeComment(@RequestBody @Valid LikeCommentRequest request) {
		return null;
	}

}
