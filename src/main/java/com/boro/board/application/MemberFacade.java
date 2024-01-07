package com.boro.board.application;

import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.domain.member.MemberInfo;
import com.boro.board.domain.member.MemberInfo.Mention;
import com.boro.board.domain.member.MemberService;
import java.util.List;
import java.util.stream.Collectors;
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

	public void signUp(SignUp signUp) {
		memberService.signUpMember(signUp);
	}

	public void setAuthentication(String phoneNumber, String password) {
		memberService.logInMember(phoneNumber, password);
	}

	public List<MemberInfo.Mention> findMemberInfosForMention(String nickNameLetter) {
		return memberService.findMembersByNickNameLetter(nickNameLetter)
				.stream().map(member -> Mention.of(member.getIdx(), member.getNickName()))
				.collect(Collectors.toList());
	}

}
