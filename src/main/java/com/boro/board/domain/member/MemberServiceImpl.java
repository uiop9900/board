package com.boro.board.domain.member;

import com.boro.board.infrastructure.member.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberReader memberReader;

	@Override
	public boolean isDuplicatedNickName(final String nickName) {
		final Integer member = memberReader.getMember(nickName);
		return member > 0;
	}
}
