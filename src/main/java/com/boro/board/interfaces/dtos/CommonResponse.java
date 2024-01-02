package com.boro.board.interfaces.dtos;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonResponse<T> {

	private Integer status;
	private String message;
	private T data;

	public static <T>CommonResponse<T> success(T data) {
		return CommonResponse.<T>builder()
				.status(HttpStatus.OK.value())
				.data(data)
				.build();
	}

	public  static CommonResponse fail(String message) {
		return CommonResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.data(null)
				.message(message)
				.build();
	}

}
