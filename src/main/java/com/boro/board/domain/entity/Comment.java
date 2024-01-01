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
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    // 부모 댓글과의 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY) // FetchType은 필요에 따라 변경 가능합니다. LAZY는 지연 로딩을 의미합니다.
    @JoinColumn(name = "parentCommentIdx") // 외래 키
    private Comment parentComment; // 대댓글을 위한 부모 댓글

    @OneToOne
    @JoinColumn(name = "post_idx") // 외래 키
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeMemberIdx")
    private Member writer; // 댓글을 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagMemberIdx")
    private Member tagMember; // 댓글을 작성한 회원

}
