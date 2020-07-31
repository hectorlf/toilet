package com.hectorlopezfernandez.blog.tag;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Business logic around Tags
 */
@Service
public class TagService {

	private final TagRepository tagRepository;

	@Inject
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	/**
	 * Returns all the tags in the system
	 */
	public List<Tag> listTags() {
		return tagRepository.findAll();
	}

	/**
	 * Returns the Tag identified by the id argument
	 */
	public Optional<Tag> getTag(String id) {
		return tagRepository.findOneById(id);
	}

	public Tag addTag(Tag tag) {
		if (tag == null) throw new IllegalArgumentException("Tag argument can't be null");
		return tagRepository.save(tag);
	}

}
