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

import com.querydsl.core.util.CollectionUtils;
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
		// 기존 해시태그 삭제
		postHashTagRepository.deletePostHashTagByPostIdx(post.getIdx());
		// 해시태그 저장
		this.saveHashTags(post, toSave);
	}

	@Override
	public void deleteHashTags(final Long postIdx) {
		postHashTagRepository.deletePostHashTagByPostIdx(postIdx);
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
