package com.boro.board.domain.post;

import com.boro.board.domain.entity.HashTag;
import com.boro.board.domain.entity.Post;
import com.boro.board.domain.post.PostCommand.Create;
import com.boro.board.infrastructure.member.Member;
import com.boro.board.infrastructure.member.MemberReader;
import com.boro.board.infrastructure.member.MemberRepository;
import com.boro.board.infrastructure.post.PostReader;
import com.boro.board.infrastructure.post.PostStore;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

	private final PostReader postReader;

	private final PostStore postStore;

	private final MemberReader memberReader;


	@Override
	@Transactional public void createPost(Create create) {
		// 게시글과 해시태그를 저장한다.

		Optional<Member> member = Optional.empty();
		if (create.getMemberIdx() != null) {
			member = memberReader.findByIdx(Long.parseLong(create.getMemberIdx()));
		}

		final Post post = create.toEntity(member);
		final List<HashTag> hashTags = create.toHashTagCommand();
		postStore.savePost(post, hashTags);
	}

	@Override public void updatePost(Create create) {
// 게시글을 수정하고 해시태그를 저장한다.

	}
}
