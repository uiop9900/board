package com.boro.board.infrastructure.member;

import com.boro.board.domain.member.Member;

public interface MemberStore {

	void save(Member member);

}
