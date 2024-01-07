package com.boro.board.domain.post;

import com.boro.board.domain.post.PostCommand.Create;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

	@Transactional void createPost(Create create);
	@Transactional void updatePost(Create create);
	@Transactional void deletePost(Long postIdx);
	PostInfo.Detail findPostDetail(Long postIdx);
	List<PostInfo.Main> findPosts(String page, String hashTag);
}
