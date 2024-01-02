package com.boro.board.domain.member;

import com.boro.board.domain.entity.Member;
import com.boro.board.domain.entity.RowStatus;
import lombok.Builder;


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
					.phoneNumber(member.getPhoneNumber())
					.password(member.getPassword())
					.rowStatus(member.getRowStatus())
					.build();
		}
	}




}
