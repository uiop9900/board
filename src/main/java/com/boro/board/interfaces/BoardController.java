package com.boro.board.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    /**
     * 게시글 단건 조회
     * 해당 게시글에 대한 내용과 댓글 전체가 나온다.
     * 제목, content, 작성자, 작성시간, 해시태그들, 좋아요 수, 댓글 전체
     */

    /**
     * 게시글 작성/수정
     * 해시태그 여러개를 넣을 수 있다.
     */


    /**
     * 게시글 삭제
     * -> 해당 게시글의 댓글도 삭제한다.
     */

    /**
     * 게시글 리스트 조회 -> 해시태그 있으면 해당 게시글 리스트나 나온다.
     * 제목, 작성자, 좋아요 수, 해시태그 목록, 댓글 수
     */


}
