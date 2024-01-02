package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.Member;
import java.util.Optional;

public interface MemberReader {

	Integer getMember(String nickName);

}
