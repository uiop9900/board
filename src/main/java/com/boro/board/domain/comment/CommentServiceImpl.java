package com.boro.board.domain.comment;

import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.entity.RowStatus;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostCommand;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.interfaces.dtos.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Optional;

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

	@Override
	@Transactional
	public void createComment(final Create create) {
		final Post post = postReader.findPostByIdx(Long.parseLong(create.getPostIdx()));
		final Member writer = memberReader.findByIdx(UserPrincipal.get().getMemberIdx());

		final Comment comment = create.toEntity(post, findCommentForReply(create.getCommentIdx()), findMemberForMention(create.getTagMemberIdx()), writer);

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
		Comment comment = commentReader.findCommentByIdx(Long.parseLong(commentIdx));

		Comment parentComment = comment.getParentComment();
		Optional<Comment> childComment = commentReader.findChildCommentByCommentIdx(Long.parseLong(commentIdx));

		if (parentComment != null && parentComment.isUnUsed()) { // 부모 코멘트가 미사용 댓글이다.
			parentComment.delete();
		}

		if (childComment.isPresent()) { // 자식 댓글이 존재 -> 미사용
			comment.isUnUsed();
		} else { // 없으면 삭제
			comment.delete();
		}
	}

	public Comment findCommentForReply(String commentIdx) {
		if (commentIdx != null) {
			return commentReader.findCommentByIdx(Long.parseLong(commentIdx));
		}

		return null;
	}

	public Member findMemberForMention(String tagMemberIdx) {
		if (tagMemberIdx != null) {
			return memberReader.findByIdx(Long.parseLong(tagMemberIdx));
		}

		return null;
	}

}
