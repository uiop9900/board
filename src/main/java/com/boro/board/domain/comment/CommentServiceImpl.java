package com.boro.board.domain.comment;

import com.boro.board.common.annotation.RedissonLock;
import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentRepository;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.interfaces.dtos.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

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
	@RedissonLock
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
		Comment comment = commentReader.findCommentByIdx(Long.parseLong(commentIdx));
		Long postIdx = comment.getPost().getIdx();

		// 나(댓글)를 제외한 ROWSTS = U인 댓글을 조회한다.
		final List<Comment> commentsExceptMe = commentReader.getCommentsExceptMeByPostIdx(postIdx, comment.getIdx());
		final boolean haveReply = commentsExceptMe.size() > 0;

		// 내가 첫 게시글인데 reply가 있으면 미사용 처리
		if (comment.isFirstComment() && haveReply) {
			comment.unUse();
			return;
		}

		// reply가 없으면, 전제 삭제 처리 한다.
		if (!haveReply) {
			comment.delete();
			// 최초 댓글을 삭제
			final Comment parentComment = commentReader.getParentCommentByPostIdx(postIdx);
			parentComment.delete();
			return;
		}

		comment.delete();
	}

	@Override
	public List<CommentInfo.Main> getCommentsMentioned() {
		return commentReader.getCommentsByTagMemberIdx(UserPrincipal.get().getMemberIdx())
				.stream().map(CommentInfo.Main::toInfo)
				.collect(Collectors.toList());
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
