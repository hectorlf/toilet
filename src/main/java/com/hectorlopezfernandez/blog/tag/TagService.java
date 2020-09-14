package com.hectorlopezfernandez.blog.tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.blog.post.PostPublicationEvent;

/**
 * Business logic around Tags
 */
@Service
public class TagService {

	private static final Logger logger = LoggerFactory.getLogger(TagService.class);

	private final TagRepository tagRepository;

	@Inject
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	/**
	 * Returns all the tags in the system
	 */
	public List<Tag> listTags() {
		logger.debug("Going into .listTags()");
		return tagRepository.findAll();
	}

	/**
	 * Returns the Tag identified by the id argument
	 */
	public Optional<Tag> getTag(String id) {
		return tagRepository.findById(id);
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

	@EventListener
	public void updateTagCount(PostPublicationEvent event) {
		logger.debug("Event of type {} received for Post Id: {}", event.getType(), event.getSource().getId());
		Collection<String> tags = event.getSource().getTags();
		if (tags != null) {
			for (String tag : tags) {
				tagRepository.updateCountBySlug(tag);
			}
		}
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Tag tag1 = new Tag("a-tag", "A tag");
		tagRepository.save(tag1);
		tagRepository.updateCountBySlug("a-tag");
		Tag tag2 = new Tag("another-tag", "Another tag");
		tagRepository.save(tag2);
		tagRepository.updateCountBySlug("another-tag");
		Tag tag3 = new Tag("not-tagged", "Not tagged");
		tagRepository.save(tag3);
		tagRepository.updateCountBySlug("not-tagged");
	}

}
