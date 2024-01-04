package com.boro.board.infrastructure.member;

import com.boro.board.domain.member.Member;
import java.util.Optional;

public interface MemberReader {

	Integer isDuplicatedByNickName(String nickName);

	Optional<Member> findByPhoneNumber(String phoneNumber);

	Member findByIdx(Long memberIdx);

}
