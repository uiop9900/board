package com.boro.board.domain.handler;

import com.boro.board.domain.exception.MemberException;
import com.boro.board.interfaces.dtos.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public CommonResponse memberHandlerException(MemberException e) {
		return CommonResponse.fail(e.getMessage());
	}


}
