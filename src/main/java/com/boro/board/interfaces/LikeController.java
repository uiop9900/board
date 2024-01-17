package com.boro.board.interfaces;

import com.boro.board.application.LikeFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.like.LikeCommentRequest;
import com.boro.board.interfaces.dtos.like.LikePostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

	private final LikeFacade likeFacade;

  /**
   * 게시글에 대한 좋아요 처리
   */
	@PostMapping("/posts/{postIdx}/likes")
	// ex) /post/{postIdx}/like
	public CommonResponse<Long> likePost(
			@PathVariable("postIdx") Long postIdx) {
		return CommonResponse.success(likeFacade.likePost(postIdx));
	}


	/**
	 * 댓글에 대한 좋아요 처리
	 */
	@PostMapping("/posts/{postIdx}/comments/{commentIdx}/likes")
	public CommonResponse<Long> likeComment(
			@PathVariable("postIdx") Long postIdx, // TODO: commentIdx만 있으면 postIdx는 필요없는 상황들이 있는데, 이런 경우 mapping을 어떻게 해야하나?
			@PathVariable("commentIdx") Long commentIdx
	) {
		return CommonResponse.success(likeFacade.likeComment(commentIdx));
	}

}
