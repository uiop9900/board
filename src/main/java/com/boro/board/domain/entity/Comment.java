package com.boro.board.domain.entity;

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

    private String content; // 댓글 내용

    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrnet_comment_idx", referencedColumnName = "comment_idx")
    private Comment parentComment; // 대댓글을 위한 부모 댓글

    @OneToOne
    @JoinColumn(name = "post_idx", referencedColumnName = "post_idx", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeMemberIdx", referencedColumnName = "member_idx", insertable = false, updatable = false)
    private Member writer; // 댓글을 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagMemberIdx", referencedColumnName = "member_idx", insertable = false, updatable = false)
    private Member tagMember; // 언급당한 회원


    // 추가적으로 대댓글들과의 관계를 설정해주는 필드도 추가할 수 있습니다.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> comments; // 대댓글들
}
