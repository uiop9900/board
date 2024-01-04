package com.boro.board.infrastructure.post;

import com.boro.board.domain.entity.HashTag;
import com.boro.board.domain.entity.Post;
import com.boro.board.domain.post.PostCommand.Create;
import com.boro.board.infrastructure.member.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {

	private final PostRepository postRepository;

	private final HashTagRepository hashTagRepository;

	@Override public void savePost(final Post post, List<HashTag> hashTags) {
		final Post saved = postRepository.save(post);
		hashTags.forEach(hashTag -> hashTag.setPost(saved));
		hashTagRepository.saveAll(hashTags);
	}

	@Override public void updatePost(final Post post) {
		//
	}
}
