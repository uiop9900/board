package com.boro.board.domain.member;

import com.boro.board.infrastructure.member.Member;
import com.boro.board.domain.member.MemberCommand.SignUp;

public interface MemberService {

	boolean isDuplicatedNickName(String nickName);

	boolean isDuplicatedPhoneNumber(String phoneNumber);

	Member getMemberByPhoneNumber(String phoneNumber);

	void signUpMember(SignUp signUp);

	MemberInfo.Main signInMember(String phoneNumber, String password);

}
