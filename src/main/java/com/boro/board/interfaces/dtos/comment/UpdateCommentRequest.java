package com.boro.board.interfaces.dtos.comment;

import com.boro.board.domain.comment.CommentCommand;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotEmpty
    private String commentIdx;
    private String content;
    private String tagMemberIdx;

    public CommentCommand.Update toCommand() {
        return CommentCommand.Update.builder()
                .commentIdx(commentIdx)
                .content(content)
                .tagMemberIdx(tagMemberIdx)
                .build();


    }
}
