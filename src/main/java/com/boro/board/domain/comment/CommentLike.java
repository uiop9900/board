package com.boro.board.domain.comment;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.member.Member;
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
@Table(name = "COMMENT_LIKE")
public class CommentLike extends BaseEntity {

    @Id
    @Column(name = "comment_like_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 댓글의 ID

    @OneToOne
    @JoinColumn(name = "comment_idx") // 외래 키
    private Comment comment; // 좋아요를 누른 회원의 ID

    @OneToOne
    @JoinColumn(name = "member_idx") // 외래 키
    private Member member; // 좋아요를 누른 회원의 ID

}
