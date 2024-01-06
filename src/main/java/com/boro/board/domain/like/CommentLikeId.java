package com.boro.board.domain.like;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class CommentLikeId implements Serializable {

    @Column(name = "comment_idx")
    private Long comment; // 좋아요를 댓글

    @Column(name = "member_idx")
    private Long member; // 좋아요를 누른 회원의 ID

}
