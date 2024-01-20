package com.boro.board.common;

public class ErrorMessage {

	// MemberException
	public final static String NOT_FOUND_MEMBER = "존재하지 않는 사용자입니다.";

	public final static String NOT_CORRECT_PASSWORD = "비밀번호가 일치하지 않습니다.";

	public final static String ONLY_VALID_MEMBER = "본인의 글만 수정가능합니다";

	// post
	public final static String NOT_FOUND_POST = "해당 게시글이 존재하지 않습니다.";

	public final static String CAN_NOT_DELETE = "본인의 게시글만 삭제가능합니다.";

	public final static String NOT_FOUND_COMMENT = "해당 댓글이 존재하지 않습니다.";

	public final static String CAN_NOT_WRITE_COMMENT = "삭제된 댓글에는 댓글작성이 불가합니다.";

	// token
	public final static String NOT_CORRECT_TOKEN = "잘못된 토큰입니다.";

	public final static String NOT_FOUND_TOKEN = "로그인을 진행해주세요.";

	// like

	public final static String CAN_NOT_CALCULATE_LIKE = "좋아요를 실패했습니다.";

	public final static String NO_LIKE_TYPE = "해당 타입이 존재하지 않습니다.";

}
