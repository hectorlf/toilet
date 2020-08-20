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

	/**
	 * Saves a tag to the database, this can result in a new row or
	 * an update to an existing one
	 */
	//FIXME add should not update
	public Tag addTag(Tag tag) {
		if (tag == null) throw new IllegalArgumentException("Tag argument can't be null");
		return tagRepository.save(tag);
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Tag tag1 = new Tag("a-tag", "A tag");
		tag1.setCount(1);
		tagRepository.save(tag1);
		Tag tag2 = new Tag("another-tag", "Another tag");
		tag2.setCount(0);
		tagRepository.save(tag2);
	}

}
