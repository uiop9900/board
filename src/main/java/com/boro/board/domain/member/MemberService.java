package com.boro.board.domain.member;

import com.boro.board.domain.member.MemberCommand.SignUp;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

	boolean isDuplicatedNickName(String nickName);

	boolean isDuplicatedPhoneNumber(String phoneNumber);

	Member getMemberByPhoneNumber(String phoneNumber);

	@Transactional void signUpMember(SignUp signUp);

	void logInMember(String phoneNumber, String password);

	List<Member> getMembersByNickNameLetter(String mentionMemberLetter);

}
