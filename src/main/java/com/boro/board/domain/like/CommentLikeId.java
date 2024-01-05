package com.boro.board.domain.like;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class CommentLikeId implements Serializable {

    @Id
    @Column(name = "comment_idx")
    private Long comment; // 좋아요를 댓글

    @Id
    @Column(name = "member_idx")
    private Long member; // 좋아요를 누른 회원의 ID

}
