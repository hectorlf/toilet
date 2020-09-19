package com.hectorlopezfernandez.blog.post;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class ArchiveEntryService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;

	@Inject
	public ArchiveEntryService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository) {
		this.postRepository = postRepository;
		this.archiveEntryRepository = archiveEntryRepository;
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		List<Post> posts = postRepository.findAll();
		posts.stream().forEach(post -> archiveEntryRepository.updateEntryByTime(post.getPublicationTime()));
	}

}
