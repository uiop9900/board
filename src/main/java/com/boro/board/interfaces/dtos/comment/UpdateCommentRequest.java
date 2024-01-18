package com.boro.board.interfaces.dtos.comment;

import com.boro.board.domain.comment.CommentCommand;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    private String content;
    private Long tagMemberIdx;

    public CommentCommand.Update toCommand(Long commentIdx) {
        return CommentCommand.Update.builder()
                .commentIdx(commentIdx)
                .content(content)
                .tagMemberIdx(tagMemberIdx)
                .build();


    }
}
