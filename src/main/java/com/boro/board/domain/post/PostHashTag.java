package com.boro.board.domain.post;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
@Table(name = "POST_HASHTAG")
public class PostHashTag extends BaseEntity {

    @EmbeddedId
    private PostHashTagId postHashTagId;

    @MapsId("post_idx")
    @ManyToOne
    @JoinColumn(name = "post_idx", referencedColumnName = "post_idx")
    private Post post;

    @MapsId("hash_tag_idx")
    @ManyToOne
    @JoinColumn(name = "hash_tag_idx", referencedColumnName = "hash_tag_idx")
    private HashTag hashTag;

    public static PostHashTag toEntity(Post post, HashTag hashTag) {
        return PostHashTag.builder()
            .postHashTagId(new PostHashTagId(post.getIdx(), hashTag.getIdx()))
            .build();
    }

}
