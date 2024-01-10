package com.boro.board.domain.entity;

import com.boro.board.domain.enums.RowStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@MappedSuperclass // 공통으로 매핑정보가 필요할 때
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Transient
    public static final String WHERE =  "row_status IN ('U', 'N')";

    @CreatedDate
//    @CreationTimestamp -> 하이버네이트 제공 어노테이션 -> 사용하지 않는 추세.
    protected LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    protected LocalDateTime updatedAt; // 수정일

    @Enumerated(EnumType.STRING)
    protected RowStatus rowStatus = RowStatus.U; // 상태 (예: 사용, 미사용, 식제 등)

}
