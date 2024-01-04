package com.boro.board.domain.member;

import static com.boro.board.domain.common.ErrorMessage.NOT_CORRECT_PASSWORD;
import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_MEMBER;

import com.boro.board.domain.common.JwtTokenUtil;
import com.boro.board.domain.common.PasswordEncrypt;
import com.boro.board.domain.config.SecretKeyConfig;
import com.boro.board.domain.exception.MemberException;
import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.member.MemberStore;
import com.boro.board.interfaces.dtos.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

	private final MemberReader memberReader;

	private final MemberStore memberStore;

	private final PasswordEncrypt passwordEncrypt;

	private final SecretKeyConfig secretKeyConfig;

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

	@Override
	@Transactional
	public void signUpMember(final SignUp signUp) {
		final String encrypt = passwordEncrypt.encrypt(signUp.getPassword());
		final Member member = signUp.toEntity(encrypt);
		memberStore.save(member);
	}

	@Override public void logInMember(final String phoneNumber, final String password) {
		// 회원 확인
		Member member = memberReader.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

		final boolean passwordMatches = passwordEncrypt.matches(password, member.getPassword());

		// 비밀번호 확인
		if (!passwordMatches) {
			throw new MemberException(NOT_CORRECT_PASSWORD);
		}

		// 인증 설정
		final UserPrincipal userPrincipal = UserPrincipal.toUserPrincipal(member);
		final Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
