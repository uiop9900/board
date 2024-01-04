package com.boro.board.interfaces;

import com.boro.board.application.CommentFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.UpsertCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		public CommonResponse createComment(@RequestBody @Valid UpsertCommentRequest request) {
			return null;
		}

    /**
     * 댓글 삭제
     */

    /**
     * 댓글 작성시, 회원 멘션을 위해 회원 조회 API
     */

    /**
     * 멘션된 사용자의 댓글을 모아보기가 가능하다.
     */

}
