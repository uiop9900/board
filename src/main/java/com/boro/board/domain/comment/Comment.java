package com.boro.board.domain.comment;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.like.CommentLike;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMENT")
@Where(clause = BaseEntity.WHERE)
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    private String content; // 댓글 내용


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx", referencedColumnName = "post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "member_idx")
    private Member writer; // 댓글을 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_member_idx", referencedColumnName = "member_idx")
    private Member tagMember; // 언급당한 회원
    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_idx", referencedColumnName = "comment_idx")
    private Comment parentComment; // 대댓글을 위한 부모 댓글

    // 대댓글
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 대댓글들(같은 depth를 children으로 인식한다.)

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> likes;

    public void update(String content, Member member) {
        this.content = content;
        this.tagMember = member;
    }


    public void unUse() {
        this.content = "댓글이 삭제되었습니다.";
        this.rowStatus = RowStatus.N;
    }

    public void delete() {
        this.rowStatus = RowStatus.D;
    }

    public Boolean isUnUsed() {
        return this.rowStatus == RowStatus.N;
    }

    public Boolean isFirstComment() {
        return this.parentComment == null;
    }
}
