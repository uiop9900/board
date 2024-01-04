package com.boro.board.interfaces.dtos;

import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.PostCommand.Create;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Builder
public class UserPrincipal {

	private Long memberIdx;
	private String phoneNumber;

	public static UserPrincipal get() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!isAuthenticated(authentication)) {
			throw new AuthenticationCredentialsNotFoundException("Not Authenticate");
		}
		return (UserPrincipal) authentication.getPrincipal();
	}

	private static boolean isAuthenticated(final Authentication authentication) {
		return authentication != null
				&& authentication.isAuthenticated() // 인증되지 않은경우.
				&& authentication.getPrincipal() != null
				&& authentication.getPrincipal() instanceof UserPrincipal;
	}

	public static UserPrincipal toUserPrincipal(Member member){
		return UserPrincipal.builder()
				.memberIdx(member.getIdx())
				.phoneNumber(member.getPhoneNumber())
				.build();
	}
}
