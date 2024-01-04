package com.boro.board.domain.post;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.post.PostCommand.Create;
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
@Table(name = "POST")
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
    private List<HashTag> hashTags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments; // 게시글 댓글들


    public void update(Create create, Member member, List<HashTag> hashTags) {
        this.title = create.getTitle();
        this.content = create.getContent();
        this.member = member;
        this.hashTags = hashTags;
    }

    public void delete() {
        this.rowStatus = RowStatus.D;
    }
}
