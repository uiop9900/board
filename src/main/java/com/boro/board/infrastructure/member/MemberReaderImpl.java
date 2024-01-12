package com.boro.board.infrastructure.member;

import static com.boro.board.common.ErrorMessage.NOT_FOUND_MEMBER;

import com.boro.board.common.exception.MemberException;
import com.boro.board.domain.member.Member;
import java.util.List;
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

	@Override public Optional<Member> findByEmail(final String email) {
		return memberRepository.findMemberByEmail(email);
	}

	@Override public Member getMemberByIdx(final Long memberIdx) {
		return memberRepository.findById(memberIdx).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
	}

	@Override
	public List<Member> findMembersByNickNameLetter(final String nickNameLetter) {
		return memberRepository.findMembersByNickNameLetter(nickNameLetter);
	}

}
