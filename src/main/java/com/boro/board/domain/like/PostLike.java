package com.boro.board.domain.like;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.entity.RowStatus;
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
@IdClass(PostLikeId.class)
public class PostLike extends BaseEntity {


    @Id
    @OneToOne
    @JoinColumn(name = "post_idx")
    private Post post; // 좋아요를 누른 게시글

    @Id
    @OneToOne
    @JoinColumn(name = "member_idx")
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
}
