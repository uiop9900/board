package com.boro.board.infrastructure.member;

import static com.boro.board.domain.member.QMember.member;

import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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

	@Override
	public List<Member> findMembersByNickNameLetter(final String nickNameLetter) {
		return queryFactory.selectFrom(member)
				.where(
						member.nickName.like("%" + nickNameLetter + "%")
								.and(member.rowStatus.eq(RowStatus.U))
				).fetch();
	}


}
