package com.hectorlopezfernandez.blog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private PostRepository postRepository;

}
