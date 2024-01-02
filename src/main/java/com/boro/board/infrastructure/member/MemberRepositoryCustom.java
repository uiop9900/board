package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.Member;
import java.util.Optional;

public interface MemberRepositoryCustom {

	Optional<Member> findMemberByPhoneNumber(String phoneNumber);

}
