package com.boro.board.infrastructure.post;

import com.boro.board.domain.post.HashTag;
import com.boro.board.domain.post.Post;
import com.boro.board.domain.post.PostHashTag;
import com.boro.board.domain.post.PostHashTag.PostHashTagBuilder;
import com.boro.board.domain.post.PostHashTagId;
import com.boro.board.infrastructure.member.PostRepository;
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

	@Override public void savePostAndHashTags(final Post post, List<HashTag> hashTags) {
		// 게시글 저장
		final Post saved = postRepository.save(post);
		this.saveHashTags(saved, hashTags);
	}

	@Override public void updateHashTags(final Post post, List<HashTag> toSave) {
		// 기존 해시태그 조회
		final List<HashTag> saved = hashTagRepository.getHashTagsByPostIdx(post.getIdx());

		for (HashTag newHashTag : toSave) { // 기존에 있는거에서 해시태그가 있는지 확인
			if (saved.contains(newHashTag)) {
			// 기존 해시태그와 동일하다.
				saved.remove(newHashTag);
			} else {
				// 기존 해시태그에서 변경되었다.
				hashTagRepository.save(newHashTag);
			}
		}

		hashTagRepository.deleteHashTagsByHashTagIdxs(saved.stream().map(hashTag -> hashTag.getIdx()).toList());
	}

	@Override
	public void deleteHashTags(final Long postIdx) {
		hashTagRepository.deleteAllHashTagsByPostIdx(postIdx);
	}

	@Override public void saveHashTags(final Post post, final List<HashTag> hashTags) {
		HashTag saved;

		for (HashTag hashTag : hashTags) {
			final Optional<HashTag> tag = hashTagRepository.findHashTagByTagTitle(hashTag.getTagTitle());

			if (tag.isEmpty()) {
				saved = hashTagRepository.save(hashTag);
			} else {
				saved = tag.get();
			}

			final PostHashTag toSave = PostHashTag.toEntity(post, saved);
			postHashTagRepository.save(toSave);
		}
	}


}
