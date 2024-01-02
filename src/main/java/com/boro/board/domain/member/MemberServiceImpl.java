package com.boro.board.domain.member;

import com.boro.board.domain.common.PasswordEncrypt;
import com.boro.board.domain.entity.Member;
import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.member.MemberStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberReader memberReader;

	private final MemberStore memberStore;
	private final PasswordEncrypt passwordEncrypt;

	@Override
	public boolean isDuplicatedNickName(final String nickName) {
		final Integer member = memberReader.isDuplicatedByNickName(nickName);
		return member > 0;
	}

	@Override public boolean isDuplicatedPhoneNumber(final String phoneNumber) {
		return memberReader.findByPhoneNumber(phoneNumber).isPresent();
	}

	@Override public Member getMemberByPhoneNumber(final String phoneNumber) {
		return memberReader.findByPhoneNumber(phoneNumber).orElseThrow(IllegalAccessError::new);
	}

	@Override public void signUpMember(final SignUp signUp) {
		final String encrypt = passwordEncrypt.encrypt(signUp.getPassword());
		final Member member = signUp.toEntity(encrypt);
		memberStore.save(member);
	}

}
