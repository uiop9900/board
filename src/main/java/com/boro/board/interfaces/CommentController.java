package com.boro.board.interfaces;

import com.boro.board.application.CommentFacade;
import com.boro.board.domain.comment.CommentInfo;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.comment.CreateCommentRequest;
import com.boro.board.interfaces.dtos.comment.DeleteCommentRequest;
import com.boro.board.interfaces.dtos.comment.UpdateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentFacade commentFacade;

    /**
     * 댓글 작성 / 수정
     * 댓글 작성시, 회원 멘션 가능
     */
  // /post/{postIdx}/comment
	@PostMapping("/posts/{postIdx}/comments")
	public CommonResponse createComment(
			@PathVariable("postIdx") Long postIdx,
			@RequestBody @Valid CreateCommentRequest request) {
		commentFacade.createComment(request.toCommand(postIdx));
		return CommonResponse.success();
	}

	/**
	 * 댓글 수정
	 */
	// ex) /posts/{postIdx}/comments/{commentIdx}
	@PatchMapping("/posts/{postIdx}/comments/{commentIdx}")
	public CommonResponse updateComment(
			@PathVariable("commentIdx") Long commentIdx,
			@RequestBody @Valid UpdateCommentRequest request) {
		commentFacade.updateComment(request.toCommand(commentIdx));
		return CommonResponse.success();
	}


    /**
     * 댓글 삭제
     */
	@DeleteMapping("/posts/{postIdx}/comments/{commentIdx}")
	public CommonResponse deleteComment(
			@PathVariable("postIdx") Long postIdx,
			@PathVariable("commentIdx") Long commentIdx
			) {
		commentFacade.deleteComment(commentIdx);
		return CommonResponse.success();
	}



  /**
   * 멘션된 사용자의 댓글을 모아보기가 가능하다.
   */
  @GetMapping("/comments/mentioned") // 내것만 가능함.
	public CommonResponse<List<CommentInfo.Main>> getCommentsMentioned() {
		return CommonResponse.success(commentFacade.getCommentsMentioned());
  }

}
