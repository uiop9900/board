package com.boro.board.domain.member;

import com.boro.board.domain.enums.RowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class MemberInfo {

	@Builder
	public static class Main {
		private Long idx;
		private String name;
		private String nickName;
		private String phoneNumber;
		private String password;

		private RowStatus rowStatus;


		public static Main toInfo(Member member) {
			return Main.builder()
					.idx(member.getIdx())
					.name(member.getName())
					.nickName(member.getNickName())
					.phoneNumber(member.getEmail())
					.password(member.getPassword())
					.rowStatus(member.getRowStatus())
					.build();
		}
	}

	@Builder
	@AllArgsConstructor(staticName = "of")
	@NoArgsConstructor
	@Getter
	public static class Mention {

		private Long memberIdx;
		private String nickName;

	}



}
