package com.boro.board.infrastructure.member;

import com.boro.board.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

	private final MemberRepository memberRepository;

	@Override public void save(final Member member) {
		memberRepository.save(member);
	}
}
