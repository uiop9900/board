package com.boro.board.domain.comment;

import com.boro.board.common.annotation.RedissonLock;
import com.boro.board.common.exception.CommentException;
import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.domain.entity.UserPrincipal;
import java.awt.Choice;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.boro.board.common.ErrorMessage.CAN_NOT_WRITE_COMMENT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

	private final CommentReader commentReader;

	private final CommentStore commentStore;

	private final PostReader postReader;

	private final MemberReader memberReader;


	@Override
	public void createComment(final Create create) {
		//  부모 댓글
		Comment parentComment = null;
		if (create.getParentCommentIdx() != null) {
			parentComment = commentReader.getCommentByIdx(create.getParentCommentIdx());
		}

		final Post post = postReader.getPostByIdx(create.getPostIdx());
		final Member writer = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());

		// comment 저장
		final Comment toSave = create.toEntity(post, parentComment, findMemberForMention(create.getTagMemberIdx()), writer);
		commentStore.save(toSave);
	}

	@Override
	@Transactional
	public void updateComment(CommentCommand.Update update) {
		Comment comment = commentReader.getCommentByIdx(update.getCommentIdx());
		comment.update(update.getContent(), findMemberForMention(update.getTagMemberIdx()));
	}

	@Override
	@Transactional
	public void deleteComment(Long commentIdx) {
		Comment comment = commentReader.getCommentByIdx(commentIdx);

		final List<Comment> children = comment.getComments();
		final List<RowStatus> list = children.stream().map(child -> child.getRowStatus()).toList();
		if (list.contains(RowStatus.U)) { // 사용중인 댓글이 있다. -> N처리
			comment.unUse();
		} else { // 사용중인 댓글이 없다 D처리
			comment.delete();
		}

		// 대댓글들 중 N 처리 된 것들은 D처리로 변경.
		final List<Comment> delete = children.stream()
				.filter(child -> child.getRowStatus() == RowStatus.N)
				.sorted(Comparator.comparing(Comment::getIdx).reversed()).toList();

		for (Comment toDelete : delete) {
			toDelete.delete();
		}
	}

	@Override
	public List<CommentInfo.Main> getCommentsMentioned() {
		return commentReader.findCommentsByTagMemberIdx(UserPrincipal.get().getMemberIdx())
				.stream().map(CommentInfo.Main::toInfo)
				.collect(Collectors.toList());
	}


	public Comment findCommentForReply(Post post) {
		Optional<Comment> recentComment = commentReader.findCommentRecentlyByPostIdx(post.getIdx());

		if (recentComment.isEmpty()) {
			return null; // 최초의 댓글
		}

		if (recentComment.get().isUnUsed()) {
			throw new CommentException(CAN_NOT_WRITE_COMMENT);
		}

		return recentComment.get();
	}

	public Member findMemberForMention(Long tagMemberIdx) {
		if (tagMemberIdx != null) {
			return memberReader.getMemberByIdx(tagMemberIdx);
		}

		return null;
	}

}
