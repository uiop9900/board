package com.boro.board.domain.like;

import com.boro.board.domain.comment.Comment;
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
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Table(name = "COMMENT_LIKE")
@IdClass(CommentLikeId.class)
public class CommentLike extends BaseEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "comment_idx") // 외래 키
    private Comment comment; // 좋아요를 누른 회원의 ID

    @Id
    @OneToOne
    @JoinColumn(name = "member_idx") // 외래 키
    private Member member; // 좋아요를 누른 회원의 ID

}
