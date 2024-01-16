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
		// 기존 해시태그 update
		final List<HashTag> saved = hashTagRepository.getHashTagsByPostIdx(post.getIdx());

		// 기존 post에 있던거 들고오기
		for (HashTag hashTag : toSave) { // 기존에 있는거에서 해시태그가 있는지 확인
			if (saved.contains(hashTag)) {
			// 포함되어있음 수정하지 않아도 된다.
			} else {

			}

		}
		tags.contains(
		// comment 확인하기

		// 수정하기

		// 기존의 해시태그 걍 삭제해도됨;

		this.deleteHashTags(post.getIdx());
		this.saveHashTags(post, hashTags);
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
