package com.boro.board.domain.member;

import com.boro.board.domain.entity.Member;
import java.util.Optional;

public interface MemberService {

	boolean isDuplicatedNickName(String nickName);

	boolean isDuplicatedPhoneNumber(String phoneNumber);

	Member getMemberByPhoneNumber(String phoneNumber);

}
