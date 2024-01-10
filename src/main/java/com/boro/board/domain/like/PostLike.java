package com.boro.board.domain.like;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.post.Post;
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
@Table(name = "POST_LIKE")
public class PostLike extends BaseEntity {

    @EmbeddedId
    private PostLikeId postLikeId;

    @OneToOne
    @MapsId("post_idx")
    @JoinColumn(name = "post_idx", referencedColumnName = "post_idx")
    private Post post; // 좋아요를 누른 게시글

    @OneToOne
    @MapsId("member_idx")
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

    public static PostLike toEntity(Member member, Post post) {
        return PostLike.builder()
                .postLikeId(new PostLikeId(post.getIdx(), member.getIdx()))
                .post(post)
                .member(member)
                .build();
    }
}
