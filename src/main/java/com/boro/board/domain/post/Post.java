package com.boro.board.domain.post;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.PostCommand.Create;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "POST")
@Where(clause = BaseEntity.WHERE)
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    private String title; // 게시글 제목
    private String content; // 게시글 내용

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 게시글 작성하는 member

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments; // 게시글 댓글들

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostHashTag> postHashTags;

    public void update(Create create) {
        this.title = create.getTitle();
        this.content = create.getContent();
    }

    public void delete() {
        this.rowStatus = RowStatus.D;
    }
}
