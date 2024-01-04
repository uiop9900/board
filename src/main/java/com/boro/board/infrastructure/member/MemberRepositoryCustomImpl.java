package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.boro.board.infrastructure.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Member> findMemberByPhoneNumber(final String phoneNumber) {
		return Optional.ofNullable(
				queryFactory.selectFrom(member)
			.where(
					member.phoneNumber.eq(phoneNumber)
						.and(member.rowStatus.eq(RowStatus.U))
			).fetchFirst()
		);
	}


}
