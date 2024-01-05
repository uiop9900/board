package com.boro.board.domain.like;

import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
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
public class PostLikeId implements Serializable {


    @Id
    @Column(name = "post_idx")
    private Post post; // 좋아요를 누른 게시글

    @Id
    @Column(name = "member_idx")
    private Member member; // 좋아요를 누른 회원의 ID

}
