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
import java.util.ArrayList;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.boro.board.common.ErrorMessage.CAN_NOT_WRITE_COMMENT;

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

//		log.error("찾는다 ㅊchildren");
//		final List<Comment> children = comment.getComments();
//		log.error("찾았다다 ㅊchildren");
//		final List<RowStatus> list = children.stream().map(child -> child.getRowStatus()).toList();

		if (hasAliveChildrenComments(comment)) { // 사용중인 댓글이 있다. -> N처리
			comment.unUse();
		} else { // 사용중인 댓글이 없다 D처리
			comment.delete();
			while (comment.getParentComment().isUnUsed()) {
				comment.getParentComment().delete();
				comment = comment.getParentComment();
			}
		}

		 // 부모댓글이 N 처리 된 것은 D처리로 변경.
//		while (comment.getComments().isEmpty() && comment.getParentComment().isUnUsed()) { // 마지막 댓글이고, 위의 댓글이 N이다.
//			comment.getParentComment().delete();
//			comment = comment.getParentComment();
//		}

	}

	private Comment getDeletableAncestorComment(Comment comment) {
		Comment parent = comment.getParentComment(); // 현재 댓글의 부모를 구함
		if(parent != null && parent.getComments().size() == 1 && parent.isUnUsed())
			// 부모가 있고, 부모의 자식이 1개(지금 삭제하는 댓글)이고, 부모의 삭제 상태가 TRUE인 댓글이라면 재귀
			return getDeletableAncestorComment(parent);
		return comment; // 삭제해야하는 댓글 반환
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
