package com.boro.board.infrastructure.member;

import com.boro.board.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

	Optional<Member> findMemberByEmail(String email);

	List<Member> findMembersByNickNameLetter(String nickNameLetter);

}
