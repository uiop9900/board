package com.boro.board.interfaces;

import com.boro.board.application.PostFacade;
import com.boro.board.domain.post.PostInfo;
import com.boro.board.interfaces.dtos.CommonResponse;
import com.boro.board.interfaces.dtos.post.DeletePostRequest;
import com.boro.board.interfaces.dtos.post.UpsertPostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

	private final PostFacade postFacade;


  /**
   * 게시글 단건 조회
   * 해당 게시글에 대한 내용과 댓글 전체가 나온다.
   * 제목, content, 작성자, 작성시간, 해시태그들, 좋아요 수, 댓글 전체
   */
  @GetMapping("/{postIdx}")
  public CommonResponse<PostInfo.Detail> findPost(@PathVariable("postIdx") Long postIdx) {
		return CommonResponse.success(postFacade.findPostDetail(postIdx));
  }


    /**
     * 게시글 작성/수정
     * 해시태그 여러개를 넣을 수 있다.
     */
	@PostMapping
	public CommonResponse upsertPost(@RequestBody @Valid UpsertPostRequest request) {
		postFacade.upsertPost(request.toCommand());
		return CommonResponse.success();
	}


    /**
     * 게시글 삭제(게시글의 댓글도 삭제한다)
     */
	@DeleteMapping
	public CommonResponse deletePost(@RequestBody @Valid DeletePostRequest request) {
		postFacade.deletePost(request.getPostIdx());
		return CommonResponse.success();
	}


  /**
   * 게시글 리스트 조회 -> 해시태그 있으면 해당 게시글 리스트나 나온다.
   * 제목, 작성자, 좋아요 수, 해시태그 목록, 댓글 수
   */
  @GetMapping(value = "/list")
	public CommonResponse<List<PostInfo.Main>> findPosts(
			@RequestParam(required = false, name = "page", defaultValue = "0") Integer page,
			@RequestParam(required = false, name = "hashTag") String hashTag) {
		  return CommonResponse.success(postFacade.findPosts(page, hashTag));
  }

}
