package com.boro.board.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String nickName; // 유저의 닉네임
    private String name; // 유저의 이름
    private String phoneNumber; // 핸드폰 번호 (ID로 사용)
    private String password; // 비밀번호

}
