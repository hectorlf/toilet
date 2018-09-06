package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ArchiveEntryRepository archiveEntryRepository;

	@Override
	public List<Post> listIndexPosts() {
		Pageable pageable = new PageRequest(0, 3);
		return postRepository.findByPublishedIsTrueOrderByPublicationDateDesc(pageable).getContent();
	}

	@Override
	public List<Post> listPostsForSitemap() {
		return postRepository.findAllByPublishedIsTrue();
	}

	@Override
	public Page<Post> listPosts(PageRequest pageAndOrder) {
		return postRepository.findAll(pageAndOrder);
	}

	@Override
	public Optional<Post> getPost(String id) {
		return postRepository.findOneById(id);
	}

	@Override
	public List<ArchiveEntry> listArchiveEntries() {
		return archiveEntryRepository.findAll();
	}

}
