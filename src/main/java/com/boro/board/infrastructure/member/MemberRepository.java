package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.Member;
import com.boro.board.domain.entity.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Integer findByNickNameAndRowStatus(String nickName, RowStatus rowStatus);

}
