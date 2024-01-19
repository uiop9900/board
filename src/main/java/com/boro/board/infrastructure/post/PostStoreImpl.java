package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostHashTag;
import com.boro.board.domain.post.PostHashTag.PostHashTagBuilder;
import com.boro.board.domain.post.PostHashTagId;
import com.boro.board.infrastructure.member.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {

	private final PostRepository postRepository;

	private final HashTagRepository hashTagRepository;

	private final PostHashTagRepository postHashTagRepository;

	@Override public void savePostAndHashTags(final Post post, List<String> hashTags) {
		// 게시글 저장
		final Post saved = postRepository.save(post);
		this.saveHashTags(saved, hashTags);
	}

	@Override public void updateHashTags(final Post post, List<String> toSave) {
		// 기존 해시태그 조회
		final List<String> saved = hashTagRepository.getHashTagsByPostIdx(post.getIdx()).stream()
				.map(s -> s.getTagTitle()).toList();

		List<Long> idxs = new ArrayList<>();

		for (String newHashTag : toSave) { // 기존에 있는거에서 해시태그가 있는지 확인
			if (saved.contains(newHashTag)) {
				// 기존 해시태그와 동일하다.
				toSave.remove(newHashTag);
				idxs.add(has
			}
		}

		this.saveHashTags(post, toSave);
		postHAsh
	}

	@Override
	public void deleteHashTags(final Long postIdx) {
		hashTagRepository.deleteAllHashTagsByPostIdx(postIdx);
	}

	@Override public void saveHashTags(final Post post, final List<String> hashTags) {
		HashTag saved;

		for (String hashTag : hashTags) {
			final Optional<HashTag> tag = hashTagRepository.findHashTagByTagTitle(hashTag);

			if (tag.isEmpty()) {
				saved = hashTagRepository.save(HashTag.toEntity(hashTag));
			} else {
				saved = tag.get();
			}

			final PostHashTag toSave = PostHashTag.toEntity(post, saved);
			postHashTagRepository.save(toSave);
		}
	}


}
