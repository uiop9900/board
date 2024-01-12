package com.boro.board.interfaces;

import com.boro.board.application.MemberFacade;
import com.boro.board.common.utils.JwtTokenUtil;
import com.boro.board.common.validator.PasswordValidator;
import com.boro.board.common.config.SecretKeyConfig;
import com.boro.board.domain.member.MemberInfo.Mention;
import com.boro.board.interfaces.dtos.member.CheckPasswordRequest;
import com.boro.board.interfaces.dtos.member.CheckUserIdRequest;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.member.SignInRequest;
import com.boro.board.interfaces.dtos.member.SignUpRequest;
import com.boro.board.interfaces.dtos.member.TokenInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	private final PasswordValidator passwordValidator;

	private final SecretKeyConfig secretKeyConfig;

	@GetMapping("/test")
	public String hello() {
	return "hello world";
	}

    /**
     * 회원가입
     */
	@PostMapping("/sign-up")
	public CommonResponse<Boolean> signUp(@RequestBody @Valid SignUpRequest request) {
		memberFacade.signUp(request.toCommand());
		return CommonResponse.success(true); // 회원가입 성공 시 true 반환
	}


    /**
     * 회원의 nickName 중복 체크
     */
	@GetMapping("/nickname/{nickname}")
	public CommonResponse<Boolean> checkNickName(@PathVariable("nickname") String nickName) {
		return CommonResponse.success(memberFacade.isDuplicatedNickName(nickName));
	}


    /**
     * 회원의 id(phoneNumber) 중복체크
     */
  @PostMapping("/email")
	public CommonResponse<Boolean> checkUserId(@RequestBody @Valid CheckUserIdRequest request) {
		return CommonResponse.success(memberFacade.isDuplicatedUserEmail(request.getEmail()));
	}


    /**
     * 회원의 비밀번호가 조건에 맞는지 확인
     */
	@PostMapping("/password")
	public CommonResponse<Boolean> checkPassword(@RequestBody @Valid CheckPasswordRequest request) {
		return CommonResponse.success(passwordValidator.checkPassword(request.getPassword()));
	}

    /**
     * 로그인
     */
	@PostMapping("/log-in")
	public CommonResponse<TokenInfo> signIn(@RequestBody @Valid SignInRequest request) {
		memberFacade.setAuthentication(request.getEmail(), request.getPassword());
		final String token = JwtTokenUtil.createToken(secretKeyConfig.getSecretKey(), request.getEmail());
		return CommonResponse.success(TokenInfo.of(token));
	}


	/**
	 * 댓글 작성시, 회원 멘션을 위해 회원 조회 API
	 */
	@PostMapping("/{nickNameLetter}")
	public CommonResponse<List<Mention>> checkMemberForMention(
			@PathVariable("nickNameLetter") String nickNameLetter)  {
		return CommonResponse.success(memberFacade.findMemberInfosForMention(nickNameLetter));
	}

}
