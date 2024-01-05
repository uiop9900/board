package com.boro.board.common.handler;

import com.boro.board.common.exception.CommentException;
import com.boro.board.common.exception.MemberException;
import com.boro.board.common.exception.PostException;
import com.boro.board.interfaces.dtos.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public CommonResponse memberHandlerException(MemberException e) {
		return CommonResponse.fail(e.getMessage());
	}

	@ExceptionHandler(PostException.class)
	public CommonResponse postHandlerException(PostException e) {
		return CommonResponse.fail(e.getMessage());
	}

	@ExceptionHandler(CommentException.class)
	public CommonResponse commentHandlerException(CommentException e) {
		return CommonResponse.fail(e.getMessage());
	}
}
