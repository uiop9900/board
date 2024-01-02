package com.boro.board.infrastructure.member;

import java.util.Optional;

public interface MemberRepositoryCustom {

	Optional<Member> findMemberByPhoneNumber(String phoneNumber);

}
