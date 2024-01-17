package com.boro.board.domain.comment;

import com.boro.board.common.annotation.RedissonLock;
import com.boro.board.common.exception.CommentException;
import com.boro.board.domain.comment.CommentCommand.Create;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.domain.entity.UserPrincipal;
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
		final Post post = postReader.getPostByIdx(Long.parseLong(create.getPostIdx()));
		final Member writer = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());

		final Comment comment = create.toEntity(post, findCommentForReply(post), findMemberForMention(create.getTagMemberIdx()), writer);

		commentStore.save(comment);
	}

	@Override
	@Transactional
	public void updateComment(CommentCommand.Update update) {
		Comment comment = commentReader.getCommentByIdx(Long.parseLong(update.getCommentIdx()));
		comment.update(update.getContent(), findMemberForMention(update.getTagMemberIdx()));
	}

	@Override
	@Transactional
	public void deleteComment(Long commentIdx) {
		Comment comment = commentReader.getCommentByIdx(commentIdx);
		Long postIdx = comment.getPost().getIdx();

		// 나(댓글)를 제외한 ROWSTS = U인 댓글을 조회한다.
		final List<Comment> commentsExceptMe = commentReader.findCommentsExceptMeByPostIdx(postIdx, comment.getIdx());
		final boolean haveReply = commentsExceptMe.size() > 0;

		// 내가 첫 게시글인데 reply가 있으면 미사용 처리
		if (comment.isFirstComment() && haveReply) {
			comment.unUse();
			return;
		}

		// reply가 없으면, 전제 삭제 처리 한다.
		if (!haveReply) {
			// 최초 댓글을 삭제
			final Comment parentComment = commentReader.findParentCommentByPostIdx(postIdx);
			parentComment.delete();
			comment.delete();
			return;
		}

		comment.delete();
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

	public Member findMemberForMention(String tagMemberIdx) {
		if (tagMemberIdx != null) {
			return memberReader.getMemberByIdx(Long.parseLong(tagMemberIdx));
		}

		return null;
	}

}
