package com.boro.board.infrastructure.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

	private final CommentRepository commentRepository;

}
