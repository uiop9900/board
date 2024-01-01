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
@Table(name = "HASHTAG")
public class HashTag extends BaseEntity {

    @Id
    @Column(name = "hash_tag_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 태그의 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private Post post; // 해당 태그가 속한 게시글 정보

    private String tagTitle; // 해시태그

}
