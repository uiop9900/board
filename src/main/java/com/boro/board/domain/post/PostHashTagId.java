package com.boro.board.domain.post;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class PostHashTagId implements Serializable {

    @Column(name = "post_idx")
    private Long post; // 게시글

    @Column(name = "hash_tag_idx")
    private Long hashTag; // 해시태그

}
