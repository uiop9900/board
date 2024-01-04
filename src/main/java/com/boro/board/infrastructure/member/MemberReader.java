package com.boro.board.infrastructure.member;

import java.util.Optional;

public interface MemberReader {

	Integer isDuplicatedByNickName(String nickName);

	Optional<Member> findByPhoneNumber(String phoneNumber);

	Member findByIdx(Long memberIdx);

}
