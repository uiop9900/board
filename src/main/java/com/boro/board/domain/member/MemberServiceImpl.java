package com.boro.board.domain.member;

import static com.boro.board.domain.common.ErrorMessage.NOT_CORRECT_PASSWORD;
import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_MEMBER;

import com.boro.board.domain.common.PasswordEncrypt;
import com.boro.board.infrastructure.member.Member;
import com.boro.board.domain.exception.MemberException;
import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.domain.member.MemberInfo.Main;
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

	@Override public MemberInfo.Main signInMember(final String phoneNumber, final String password) {
		final Member member = memberReader.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
		final boolean passwordMatches = passwordEncrypt.matches(password, member.getPassword());

		if (!passwordMatches) {
			throw new MemberException(NOT_CORRECT_PASSWORD);
		}

		return Main.toInfo(member);
	}

}
