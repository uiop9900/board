package com.boro.board.domain.member;

import static com.boro.board.common.ErrorMessage.NOT_CORRECT_PASSWORD;
import static com.boro.board.common.ErrorMessage.NOT_FOUND_MEMBER;

import com.boro.board.common.utils.PasswordEncrypt;
import com.boro.board.common.exception.MemberException;
import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.member.MemberStore;
import com.boro.board.domain.entity.UserPrincipal;
import java.util.List;
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


	@Override
	public boolean isDuplicatedNickName(final String nickName) {
		final Integer member = memberReader.isDuplicatedByNickName(nickName);
		return member > 0;
	}

	@Override public boolean isDuplicatedEmail(final String email) {
		return memberReader.findByEmail(email).isPresent();
	}

	@Override public Member getMemberByEmail(final String email) {
		return memberReader.findByEmail(email).orElseThrow(IllegalAccessError::new);
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
		Member member = memberReader.findByEmail(phoneNumber).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

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

	@Override public List<Member> findMembersByNickNameLetter(final String nickNameLetter) {
		return memberReader.findMembersByNickNameLetter(nickNameLetter);
	}

}
