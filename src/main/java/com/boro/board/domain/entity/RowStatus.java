package com.boro.board.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RowStatus {
    U("사용중"),
    N("미사용"), // TODO: 미사용 경우 없으면 추후 삭제
    D("삭제");

    private final String description;
}
