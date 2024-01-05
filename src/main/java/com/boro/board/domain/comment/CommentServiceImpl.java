package com.boro.board.domain.comment;

import static com.boro.board.domain.common.ErrorMessage.NOT_FOUND_COMMENT;

import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.exception.CommentException;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentRepository;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.interfaces.dtos.UserPrincipal;
import com.querydsl.core.types.dsl.ComparableEntityPath;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

	private final CommentReader commentReader;

	private final CommentStore commentStore;

	private final PostReader postReader;

	private final MemberReader memberReader;
	private final CommentRepository commentRepository;

	@Override
	@Transactional
	public void createComment(final Create create) {
		final Post post = postReader.findPostByIdx(Long.parseLong(create.getPostIdx()));
		final Member writer = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());

		final Comment comment = create.toEntity(post, findCommentForReply(create.getParentCommentIdx()), findMemberForMention(create.getTagMemberIdx()), writer);

		commentStore.save(comment);
	}

	@Override
	@Transactional
	public void updateComment(CommentCommand.Update update) {
		Comment comment = commentReader.findCommentByIdx(Long.parseLong(update.getCommentIdx()));
		comment.update(update.getContent(), findMemberForMention(update.getTagMemberIdx()));
	}

	@Override
	@Transactional
	public void deleteComment(String commentIdx) {
		// 삭제할 코멘트
		Comment comment = commentReader.findCommentByIdx(Long.parseLong(commentIdx));
		Long postIdx = comment.getPost().getIdx();

		// 해당 게시글의 모든 살아있는 댓글을 가지고 온다.
		final List<Comment> childrenComment = commentReader.getChildCommentsByPostIdx(postIdx, comment.getIdx());

		final boolean haveReply = childrenComment.size() > 0; // 대댓글이 존재한다.

		// 내가 첫 게시글인데 reply가 있으면 미사용
		if (comment.isFirstComment() && haveReply) {
			comment.unUse();
			return;
		}

		// 나머지는 삭제 처리하는데, 모든 댓글이 삭제면 N으로 된거  삭제처리 한다.

		if (!haveReply) {
			// 삭제 처리
			comment.delete();
			// 최초 댓글을 삭제처리한다.
			final Comment parentComment = commentReader.getParentCommentByPostIdx(postIdx);
			parentComment.delete();
			return;
		}

		comment.delete();
	}


	public Comment findCommentForReply(String commentIdx) {
		if (commentIdx != null) {
			return commentReader.findCommentByIdx(Long.parseLong(commentIdx));
		}

		return null;
	}

	public Member findMemberForMention(String tagMemberIdx) {
		if (tagMemberIdx != null) {
			return memberReader.getMemberByIdx(Long.parseLong(tagMemberIdx));
		}

		return null;
	}

}
