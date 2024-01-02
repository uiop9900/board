package com.boro.board.domain.entity;

import com.boro.board.infrastructure.member.Member;
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
@Table(name = "POST_LIKE")
public class PostLike extends BaseEntity {

    @Id
    @Column(name = "post_like_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 댓글의 ID

    @OneToOne
    @JoinColumn(name = "post_idx")
    private Post post; // 좋아요를 누른 게시글

    @OneToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 좋아요를 누른 회원의 ID

}
