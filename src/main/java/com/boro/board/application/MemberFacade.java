package com.boro.board.application;

import com.boro.board.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberService memberService;

	public boolean isDuplicatedNickName(String nickName) {
		return memberService.isDuplicatedNickName(nickName);
	}

	public boolean isDuplicatedUserPhoneNumber(String phoneNumber) {
		return memberService.isDuplicatedPhoneNumber(phoneNumber);
	}

}
