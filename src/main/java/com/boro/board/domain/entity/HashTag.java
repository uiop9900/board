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

    private String tagTitle; // 해시태그 해시태그를 검색할때, 그게 정확히 있어야 검색, 포함은 안됨 -> 양평숙소 -> 양평숙소만 나온다. -> #양평숙소들 안나옴

    public void setPost(Post post) {
        this.post = post;
    }

}
