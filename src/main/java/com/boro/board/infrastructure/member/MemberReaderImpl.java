package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.RowStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

	private final MemberRepository memberRepository;

	@Override public Integer isDuplicatedByNickName(final String nickName) {
		return memberRepository.countMemberByNickName(nickName);
	}

	@Override public Optional<Member> findByPhoneNumber(final String phoneNumber) {
		return memberRepository.findMemberByPhoneNumber(phoneNumber);
	}

	@Override public Optional<Member> findByIdx(final Long memberIdx) {
		return memberRepository.findById(memberIdx);
	}

}
