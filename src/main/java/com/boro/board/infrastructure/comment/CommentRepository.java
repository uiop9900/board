package com.boro.board.infrastructure.comment;

import com.boro.board.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    List<Comment> findCommentsByTagMemberIdx(Long tagMemberIdx);

    Optional<Comment> getCommentsByParentCommentIdx(Long commentIdx);

}
