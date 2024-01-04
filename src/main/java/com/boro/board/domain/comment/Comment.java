package com.boro.board.domain.comment;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.member.Member;
import jakarta.persistence.*;
import java.util.List;
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
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    private String content; // 댓글 내용원

    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parennt_comment_idx", referencedColumnName = "comment_idx")
    private Comment parentComment; // 대댓글을 위한 부모 댓글

    @ManyToOne
    @JoinColumn(name = "post_idx", referencedColumnName = "post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "member_idx")
    private Member writer; // 댓글을 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_member_idx", referencedColumnName = "member_idx")
    private Member tagMember; // 언급당한 회원

    // 추가적으로 대댓글들과의 관계를 설정해주는 필드도 추가할 수 있습니다.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> comments; // 대댓글들

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
