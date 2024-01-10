package com.boro.board.domain.like;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.enums.RowStatus;
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
public class CommentLike extends BaseEntity {


    @EmbeddedId
    private CommentLikeId commentLikeId;

    @MapsId("comment_idx")
    @ManyToOne
    @JoinColumn(name = "comment_idx", referencedColumnName = "comment_idx")
    private Comment comment; // 좋아요를 누른 댓글

    @MapsId("member_idx")
    @ManyToOne
    @JoinColumn(name = "member_idx", referencedColumnName = "member_idx")
    private Member member; // 좋아요를 누른 회원의 ID


    public boolean isUnLiked() {
        return this.rowStatus == RowStatus.D;
    }

    public void unLike() {
        this.rowStatus = RowStatus.D;
    }

    public void like() {
        this.rowStatus = RowStatus.U;
    }

    public static CommentLike toEntity(Member member, Comment comment) {
        return CommentLike.builder()
                .commentLikeId(new CommentLikeId(comment.getIdx(), member.getIdx()))
                .comment(comment)
                .member(member)
                .build();
    }

}
