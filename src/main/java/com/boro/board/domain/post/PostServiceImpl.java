package com.boro.board.domain.post;

import com.boro.board.domain.post.PostCommand.Create;
import com.boro.board.domain.member.Member;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.infrastructure.post.PostStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostReader postReader;

	private final PostStore postStore;

	private final MemberReader memberReader;


	@Override
	@Transactional public void createPost(Create create) {
		final Post post = create.toEntity(findMemberForMention(create));
		final List<HashTag> hashTags = create.toHashTagCommand();
		postStore.savePostAndHashTags(post, hashTags);
	}

	@Override
	@Transactional public void updatePost(Create create) {
		// 게시글 update
		final Post post = postReader.findPostByIdx(Long.parseLong(create.getPostIdx()));
		final List<HashTag> hashTags = create.toHashTagCommand();

		post.update(create, findMemberForMention(create), hashTags);

		// 해시태그 update
		postStore.updateHashTags(post, hashTags);
	}

	@Override
	@Transactional public void deletePost(final Long postIdx) {
		final Post post = postReader.findPostByIdx(postIdx);
		post.delete();
		postStore.deleteHashTags(postIdx);
	}

	public Member findMemberForMention(Create create) {
		if (create.getMemberIdx() != null) {
			return memberReader.findByIdx(Long.parseLong(create.getMemberIdx()));
		}

		return null;

	}
}
