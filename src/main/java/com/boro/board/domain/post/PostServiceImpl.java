package com.boro.board.domain.post;

import com.boro.board.common.exception.MemberException;
import com.boro.board.domain.comment.Comment;
import com.boro.board.domain.comment.CommentInfo;
import com.boro.board.domain.comment.CommentInfo.Detail;
import com.boro.board.domain.entity.UserPrincipal;
import com.boro.board.domain.enums.RowStatus;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.HashTagInfo.Main;
import com.boro.board.domain.post.PostCommand.Create;
import com.boro.board.infrastructure.comment.CommentReader;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.like.LikeReader;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.infrastructure.post.PostStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.boro.board.common.ErrorMessage.CAN_NOT_DELETE;
import static com.boro.board.common.ErrorMessage.ONLY_VALID_MEMBER;
import static com.boro.board.common.property.RedisKeyProperties.POST_LIKE_REDIS_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

	private final PostReader postReader;

	private final PostStore postStore;

	private final MemberReader memberReader;

	private final CommentStore commentStore;

	private final LikeReader likeReader;

	private final CommentReader commentReader;

	private final static Integer PAGE_SIZE = 10;


	@Override
	@Transactional public void createPost(Create create) {
		Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = create.toEntity(member);
		final List<String> hashTags = create.toHashTagStrings();
		postStore.savePostAndHashTags(post, hashTags);
	}

	@Override
	@Transactional public void updatePost(Create create) {
		// 게시글 update
		final Post post = postReader.getPostByIdx(create.getPostIdx());

		// 작성자 확인
		if (UserPrincipal.get().getMemberIdx() != post.getMember().getIdx()) {
			throw new MemberException(ONLY_VALID_MEMBER);
		}

		// post update
		post.update(create);

		// 해시태그 update
		final List<String> hashTags = create.toHashTagStrings();
		postStore.updateHashTags(post, hashTags);
	}

	@Override
	@Transactional
	public void deletePost(final Long postIdx) {
		Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());

		final Post post = postReader.getPostByIdx(postIdx);
		if (post.getMember().getIdx() != member.getIdx()) {
			throw new MemberException(CAN_NOT_DELETE);
		}

		// post 삭제
		post.delete();

		// hashtag 삭제
		postStore.deleteHashTags(postIdx);

		// comment 삭제
		final List<Long> commentIdxs = post.getComments().stream().map(comment -> comment.getIdx())
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		commentStore.deleteComments(commentIdxs);
	}

	@Override
	public PostInfo.Detail findPostDetail(Long postIdx) {
		// post
		Post post = postReader.getPostByIdx(postIdx);
		final Long postLikes = likeReader.getPostLikesNumber(postIdx);

		// hashTag
		final List<Main> hashTags = post.getPostHashTags().stream()
				.filter(postHashTag -> postHashTag.getRowStatus() == RowStatus.U)
				.map(hashTag -> Main.toInfo(hashTag.getHashTag())).toList();

		// comment
		final List<Comment> comments = commentReader.findCommentsSortedByPostIdx(postIdx);
		final Map<Long, Long> commentLikes = likeReader.getCommentsLikesNumber(comments.stream().map(comment -> comment.getIdx()).toList());

		List<CommentInfo.Detail> infos = new ArrayList<>();
		Map<Long, CommentInfo.Detail> result = new HashMap<>();

		comments.stream().forEach(comment -> {
					final Detail info = Detail.toInfo(comment, commentLikes.get(comment.getIdx()));
					result.put(info.getCommentIdx(), info);

					if (comment.getParentComment() != null) {
						result.get(comment.getParentComment().getIdx()).getChildren().add(info);
					} else {
						infos.add(info);
					}
				});

		return PostInfo.Detail.toInfo(post, postLikes, hashTags, infos);
	}

	@Override
	public List<PostInfo.Main> findPosts(Integer page, String hashTag) {
		// 페이징
		PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);

		// 해시태그로 post 조회
		Page<Post> posts = postReader.findPostsByHashTag(hashTag, pageRequest);

		// post별 해시태그 조회
		Map<Long, List<Main>> hashTags = new HashMap<>();
		for (Post post : posts) {
			List<Main> tags = post.getPostHashTags().stream()
					.filter(postHashTag -> postHashTag.getRowStatus() == RowStatus.U)
					.map(tag -> Main.toInfo(tag.getHashTag())).toList();
			hashTags.put(post.getIdx(), tags);
		}

		// post별 좋아요 조회
		List<Long> postIdxs = posts.stream().map(Post::getIdx).toList();
		Map<Long, Long> likeNumbers = likeReader.getPostsLikesNumber(postIdxs);

		return posts.stream()
				.map(post -> PostInfo.Main.toInfo(post,
						likeNumbers.get(post.getIdx()),
						hashTags.get(post.getIdx()))
				)
				.toList();
	}

}
