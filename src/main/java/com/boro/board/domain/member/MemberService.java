package com.boro.board.domain.member;

import com.boro.board.infrastructure.member.Member;
import com.boro.board.domain.member.MemberCommand.SignUp;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

	boolean isDuplicatedNickName(String nickName);

	boolean isDuplicatedPhoneNumber(String phoneNumber);

	Member getMemberByPhoneNumber(String phoneNumber);

	@Transactional void signUpMember(SignUp signUp);

	MemberInfo.Main logInMember(String phoneNumber, String password);

}
