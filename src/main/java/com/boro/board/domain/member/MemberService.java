package com.boro.board.domain.member;

import com.boro.board.domain.entity.Member;
import com.boro.board.domain.member.MemberCommand.SignUp;
import java.util.Optional;

public interface MemberService {

	boolean isDuplicatedNickName(String nickName);

	boolean isDuplicatedPhoneNumber(String phoneNumber);

	Member getMemberByPhoneNumber(String phoneNumber);

	void signUpMember(SignUp signUp);

}
