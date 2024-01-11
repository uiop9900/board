package com.boro.board.domain.post;

import com.boro.board.common.exception.MemberException;
import com.boro.board.domain.comment.CommentInfo;
import com.boro.board.domain.member.Member;
import com.boro.board.domain.post.PostCommand.Create;
import com.boro.board.infrastructure.comment.CommentStore;
import com.boro.board.infrastructure.like.LikeReader;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.infrastructure.post.PostStore;
import com.boro.board.domain.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.boro.board.common.ErrorMessage.CAN_NOT_DELETE;
import static com.boro.board.common.property.RedisKeyProperties.COMMENT_LIKE_REDIS_KEY;
import static com.boro.board.common.property.RedisKeyProperties.POST_LIKE_REDIS_KEY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostReader postReader;

	private final PostStore postStore;

	private final MemberReader memberReader;

	private final CommentStore commentStore;

	private final LikeReader likeReader;

	private final static Integer PAGE_SIZE = 10;


	@Override
	@Transactional public void createPost(Create create) {
		Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		final Post post = create.toEntity(member);
		final List<HashTag> hashTags = create.toHashTagCommand();
		postStore.savePostAndHashTags(post, hashTags);
	}

	@Override
	@Transactional public void updatePost(Create create) {
		// 게시글 update
		final Post post = postReader.getPostByIdx(Long.parseLong(create.getPostIdx()));
		final List<HashTag> hashTags = create.toHashTagCommand();

		Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());
		post.update(create, member, hashTags);

		// 해시태그 update
		postStore.updateHashTags(post, hashTags);
	}

	@Override
	@Transactional public void deletePost(final Long postIdx) {
		Member member = memberReader.getMemberByIdx(UserPrincipal.get().getMemberIdx());

		final Post post = postReader.getPostByIdx(postIdx);
		if (post.getMember().getIdx() != member.getIdx()) {
			throw new MemberException(CAN_NOT_DELETE);
		}

		post.delete();
		postStore.deleteHashTags(postIdx);
		final List<Long> commentIdxs = post.getComments().stream().map(comment -> comment.getIdx())
				.sorted(Comparator.reverseOrder()) .collect(Collectors.toList());

		commentStore.deleteComments(commentIdxs);
	}

	@Override
	public PostInfo.Detail findPostDetail(Long postIdx) {
		Post post = postReader.getPostByIdx(postIdx);
		Long postLikes = likeReader.getLikeNumber(post.getIdx(), POST_LIKE_REDIS_KEY);

		List<CommentInfo.Detail> commentInfos = post.getComments().stream()
				.map(comment -> CommentInfo.Detail.toInfo(comment,
						likeReader.getLikeNumber(comment.getIdx(), COMMENT_LIKE_REDIS_KEY)
						)).toList();

		return PostInfo.Detail.toInfo(post, commentInfos, postLikes);
	}

	@Override
	public List<PostInfo.Main> findPosts(Integer page, String hashTag) {
		Page<Post> posts;
		PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);

		if (hashTag == null) {
			posts = postReader.findPosts(pageRequest);
		} else {
			posts = postReader.findPostsByHashTag(hashTag, pageRequest);
		}

		List<Long> postIdxs = posts.stream().map(Post::getIdx).toList();
		Map<Long, Long> likeNumbers = likeReader.getLikeNumbers(postIdxs, POST_LIKE_REDIS_KEY);

		return posts.stream()
				.map(post -> PostInfo.Main.toInfo(post,
						likeNumbers.get(post.getIdx())))
				.toList();
	}

}
