package com.boro.board.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeType {

    POST("게시글"),
    COMMENT("댓글");

    private final String description;
}
