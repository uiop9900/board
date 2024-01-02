package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.RowStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	Integer countMemberByNickName(String nickName);

	Optional<Member> findMemberByPhoneNumber(String phoneNumber);

}
