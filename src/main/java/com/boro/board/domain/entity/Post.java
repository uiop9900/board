package com.boro.board.domain.entity;

import com.boro.board.infrastructure.member.Member;
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

    @OneToOne
    @JoinColumn(name = "member_idx")
    private Member member; // 게시글 작성하는 member

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // 기존꺼 삭제
    private List<HashTag> hashTags;
}
