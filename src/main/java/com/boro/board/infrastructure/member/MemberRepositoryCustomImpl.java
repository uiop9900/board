package com.boro.board.infrastructure.member;

import static com.boro.board.domain.entity.QMember.member;

import com.boro.board.domain.entity.RowStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
