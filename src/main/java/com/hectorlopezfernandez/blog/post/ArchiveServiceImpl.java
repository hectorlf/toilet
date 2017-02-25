package com.hectorlopezfernandez.blog.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> listIndexPosts() {
		Pageable pageable = new PageRequest(0, 3);
		return postRepository.findByPublishedIsTrueOrderByPublicationDateDesc(pageable).getContent();
	}

	@Override
	public List<Post> listPostsForSitemap() {
		return postRepository.findAllByPublishedIsTrue();
	}

}
