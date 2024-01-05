package com.boro.board.domain.like;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class PostLikeId implements Serializable {

    @Column(name = "post_idx")
    private Long post; // 좋아요를 누른 게시글

    @Column(name = "member_idx")
    private Long member; // 좋아요를 누른 회원의 ID

    // 복합키는 해당 엔티티의 Pk를 참조하는거라서 Post, Member가 아니라 Long으로 받는다.

}
