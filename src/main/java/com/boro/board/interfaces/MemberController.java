package com.boro.board.interfaces;

import com.boro.board.application.MemberFacade;
import com.boro.board.domain.common.PasswordValidator;
import com.boro.board.interfaces.dtos.CheckPasswordRequest;
import com.boro.board.interfaces.dtos.CheckUserIdRequest;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	private final PasswordValidator passwordValidator;

	@GetMapping("/test")
	public String hello() {
		return "hello world";
	}

    /**
     * 회원가입
     */
		@PostMapping("/sign-up")
		public CommonResponse<Boolean> signUp(SignUpRequest request) {
			memberFacade.signUp(request.toCommand());
			return CommonResponse.success(true); // 회원가입 성공 시 true 반환
		}


    /**
     * 회원의 nickName 중복 체크
     */
		@GetMapping("/nickname/{}")
		public CommonResponse<Boolean> checkNickName(@PathVariable("nickname") String nickName) {
			return CommonResponse.success(memberFacade.isDuplicatedNickName(nickName));
		}


    /**
     * 회원의 id(phoneNumber) 중복체크
     */
    @PostMapping("/phoneNumber")
		public CommonResponse<Boolean> checkUserId(CheckUserIdRequest request) {
			return CommonResponse.success(memberFacade.isDuplicatedUserPhoneNumber(request.getPhoneNumber()));
		}


    /**
     * 회원의 비밀번호가 조건에 맞는지 확인
     */
		@PostMapping("/password")
		public CommonResponse<Boolean> checkPassword(CheckPasswordRequest request) {
			return CommonResponse.success(passwordValidator.checkPassword(request.getPassword()));
		}

    /**
     * 로그인
     */


}
