package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import com.boro.board.domain.post.Post;
import com.boro.board.infrastructure.member.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {

	private final PostRepository postRepository;

	private final HashTagRepository hashTagRepository;

	@Override public void savePostAndHashTags(final Post post, List<HashTag> hashTags) {
		// 게시글 저장
		final Post saved = postRepository.save(post);
		this.saveHashTags(saved, hashTags);
	}

	@Override public void updateHashTags(final Post post, List<HashTag> hashTags) {
		// 기존 해시태그 삭제
		this.deleteHashTags(post.getIdx());
		this.saveHashTags(post, hashTags);
	}

	@Override
	public void deleteHashTags(final Long postIdx) {
		hashTagRepository.deleteAllHashTagsByPostIdx(postIdx);
	}

	@Override public void saveHashTags(final Post post, final List<HashTag> hashTags) {
		// 해시태그 저장
		hashTags.forEach(hashTag -> hashTag.setPost(post));
		hashTagRepository.saveAll(hashTags);
	}


}
