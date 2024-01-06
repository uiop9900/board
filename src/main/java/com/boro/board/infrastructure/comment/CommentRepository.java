package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.member.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    List<Comment> getCommentsByTagMemberIdx(Long tagMemberIdx);

}
