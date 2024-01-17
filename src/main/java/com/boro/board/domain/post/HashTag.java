package com.boro.board.domain.post;

import com.boro.board.domain.entity.BaseEntity;
import com.boro.board.domain.enums.RowStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HASHTAG")
@Where(clause = BaseEntity.WHERE)
public class HashTag extends BaseEntity {

    @Id
    @Column(name = "hash_tag_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 태그의 고유 식별자

    private String tagTitle; // 해시태그 해시태그를 검색할때, 그게 정확히 있어야 검색, 포함은 안됨 -> 양평숙소 -> 양평숙소만 나온다. -> #양평숙소들 안나옴

    public static void deleteHashTags(List<HashTag> hashTags) {
        for (HashTag hashTag : hashTags) {
            hashTag.rowStatus = RowStatus.D;
        }
    }

}
