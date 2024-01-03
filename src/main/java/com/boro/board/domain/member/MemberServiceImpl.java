package com.boro.board.domain.member;

import static com.boro.board.domain.common.ErrorMessage.NOT_CORRECT_PASSWORD;
import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_MEMBER;

import com.boro.board.domain.common.JwtTokenProvider;
import com.boro.board.domain.common.PasswordEncrypt;
import com.boro.board.infrastructure.member.Member;
import com.boro.board.domain.exception.MemberException;
import com.boro.board.domain.member.MemberCommand.SignUp;
import com.boro.board.domain.member.MemberInfo.Main;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.member.MemberStore;
import com.boro.board.interfaces.dtos.TokenInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService, UserDetailsService {

	private final MemberReader memberReader;

	private final MemberStore memberStore;
	private final PasswordEncrypt passwordEncrypt;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final AuthenticationManager authenticationManager;
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

	@Override public MemberInfo.Main logInMember(final String phoneNumber, final String password) {

		// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
		// 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password);

		// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenInfo tokenInfo = JwtTokenProvider.generateToken(authentication);

		return tokenInfo;

		final Member member = memberReader.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
		final boolean passwordMatches = passwordEncrypt.matches(password, member.getPassword());

		if (!passwordMatches) {
			throw new MemberException(NOT_CORRECT_PASSWORD);
		}

		return Main.toInfo(member);
	}


	public void signInAndGrantAuthority(String phoneNumber, String password) {
		// 인증을 위한 토큰 생성
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(phoneNumber, password);

		// 사용자 인증
		Authentication authentication = authenticationManager.authenticate(authToken);

		// 사용자의 권한 설정 (예: ROLE_USER)
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 권한 부여

		// 사용자 정보와 권한을 저장한 인증 객체 생성
		Authentication authenticated = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);

		// SecurityContext에 사용자 인증 정보 설정
		SecurityContextHolder.getContext().setAuthentication(authenticated);
	}

	@Override public UserDetails loadUserByUsername(final String phoneNumber) throws UsernameNotFoundException {

		final Member member = memberReader.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
		final boolean passwordMatches = passwordEncrypt.matches(password, member.getPassword());

		if (!passwordMatches) {
			throw new MemberException(NOT_CORRECT_PASSWORD);
		}

		return Main.toInfo(member);
	}
}
