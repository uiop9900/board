package com.boro.board.interfaces;

import com.boro.board.application.MemberFacade;
import com.boro.board.interfaces.dtos.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;
    /**
     * 회원가입
     */



    /**
     * 회원의 nickName 중복 체크
     */
		@GetMapping("/nickname/{}")
		public CommonResponse checkNickName(@PathVariable("nickname") String nickName) {
			return CommonResponse.success(memberFacade.isDuplicatedNickName(nickName));
		}


    /**
     * 회원의 id(phoneNumber) 중복체크
     */


    /**
     * 회원의 비밀번호가 조건에 맞는지 확인
     */

    /**
     * 로그인
     */

}
