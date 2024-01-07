package com.boro.board.infrastructure.member;

import com.boro.board.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberReader {

	Integer isDuplicatedByNickName(String nickName);

	Optional<Member> findByPhoneNumber(String phoneNumber);

	Member getMemberByIdx(Long memberIdx);

	List<Member> findMembersByNickNameLetter(String nickNameLetter);

}
