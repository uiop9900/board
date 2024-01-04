package com.boro.board.domain.comment;

import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostCommand;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.interfaces.dtos.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
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

		final Comment comment = create.toEntity(post, findCommentForReply(create), findMemberForMention(create), writer);

		commentStore.save(comment);
	}

	public Comment findCommentForReply(Create create) {
		if (create.getParentCommentIdx() != null) {
			return commentReader.findCommentByIdx(Long.parseLong(create.getParentCommentIdx()));
		}

		return null;
	}

	public Member findMemberForMention(Create create) {
		if (create.getTagMemberIdx() != null) {
			return memberReader.findByIdx(Long.parseLong(create.getTagMemberIdx()));
		}

		return null;
	}

}
