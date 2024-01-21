package com.boro.board.domain.comment;

import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.entity.UserPrincipal;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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

		// 자식 댓글 확인
		if (hasAliveChildrenComments(comment)) { // 사용중인 댓글이 있다. -> N처리
			comment.unUse();
		} else {
			// D 처리시, 부모 댓글 중 N 처리를 D로 변경
			comment.delete();
			while (comment.getParentComment().isUnUsed()) {
				comment.getParentComment().delete();
				comment = comment.getParentComment();
			}
		}
	}

	private Boolean hasAliveChildrenComments(Comment comment) {
		List<Comment> children = new ArrayList<>();

		List<RowStatus> rows = comment.getComments().stream().map(c -> c.getRowStatus()).toList();
		if (rows.contains(RowStatus.U)) {
			return true;
		}

		Optional<Comment> commentByParentCommentIdx = commentReader.getCommentByParentCommentIdx(comment.getIdx());

		if (commentByParentCommentIdx.isPresent()) {
			while (commentByParentCommentIdx.isPresent()) {
				children.add(commentByParentCommentIdx.get());
				commentByParentCommentIdx = commentReader.getCommentByParentCommentIdx(commentByParentCommentIdx.get().getIdx());
			}
		}

		List<RowStatus> list = children.stream().map(c -> c.getRowStatus()).toList();
		return list.contains(RowStatus.U);
	}

	@Override
	public List<CommentInfo.Main> getCommentsMentioned() {
		return commentReader.findCommentsByTagMemberIdx(UserPrincipal.get().getMemberIdx())
				.stream().map(CommentInfo.Main::toInfo)
				.collect(Collectors.toList());
	}


	public Member findMemberForMention(Long tagMemberIdx) {
		if (tagMemberIdx != null) {
			return memberReader.getMemberByIdx(tagMemberIdx);
		}

		return null;
	}

}
