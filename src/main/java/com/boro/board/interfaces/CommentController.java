package com.boro.board.interfaces;

import com.boro.board.application.CommentFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.CreateCommentRequest;
import com.boro.board.interfaces.dtos.DeleteCommentRequest;
import com.boro.board.interfaces.dtos.UpdateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentFacade commentFacade;

    /**
     * 댓글 작성 / 수정
     * 댓글 작성시, 회원 멘션 가능
     */
	@PostMapping
	public CommonResponse createComment(@RequestBody @Valid CreateCommentRequest request) {
		commentFacade.createComment(request.toCommand());
		return CommonResponse.success();
	}

	/**
	 * 댓글 수정
	 */
	@PatchMapping
	public CommonResponse updateComment(@RequestBody @Valid UpdateCommentRequest request) {
		commentFacade.updateComment(request.toCommand());
		return CommonResponse.success();
	}


    /**
     * 댓글 삭제
     */
	@DeleteMapping
	public CommonResponse deleteComment(@RequestBody @Valid DeleteCommentRequest request) {
		commentFacade.deleteComment(request.getCommentIdx());
		return CommonResponse.success();
	}


    /**
     * 댓글 작성시, 회원 멘션을 위해 회원 조회 API
     */


    /**
     * 멘션된 사용자의 댓글을 모아보기가 가능하다.
     */

}
