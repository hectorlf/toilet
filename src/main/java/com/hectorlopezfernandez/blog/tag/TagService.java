package com.hectorlopezfernandez.blog.tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.blog.DocumentNotFoundException;
import com.hectorlopezfernandez.blog.post.PostPublicationEvent;

/**
 * Business logic around Tags
 */
@Service
public class TagService {

	private static final Logger logger = LoggerFactory.getLogger(TagService.class);

	private final TagRepository tagRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Inject
	public TagService(TagRepository tagRepository, ApplicationEventPublisher eventPublisher) {
		this.tagRepository = tagRepository;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * Returns all the tags in the system. Never null.
	 */
	public List<Tag> listTags() {
		logger.debug("Going into .listTags()");
		return tagRepository.findAll();
	}

	/**
	 * Returns the Tag identified by the id argument.
	 */
	public Optional<Tag> getTag(String id) {
		return tagRepository.findById(id);
	}

	/**
	 * Saves a tag to the database, this can result in a new row or
	 * an update to an existing one.
	 */
	//FIXME add should not update
	public Tag addTag(Tag tag) {
		if (tag == null) throw new IllegalArgumentException("Tag argument can't be null");
		logger.debug("Adding Tag using values [{}, {}]", tag.getName(), tag.getSlug());
		return tagRepository.save(tag);
	}

	/**
	 * Updates a tag in the database. If the id doesn't exist, an exception is thrown.
	 */
	public Tag editTag(Tag tag) {
		if (tag == null) throw new IllegalArgumentException("Tag argument can't be null");
		if (tag.getId() == null || tag.getId().isEmpty()) throw new DocumentNotFoundException("Id of the tag cannot be empty");
		logger.debug("Editing Tag with id: {}, using values [{}, {}]", tag.getId(), tag.getName(), tag.getSlug());

		Tag existingTag = tagRepository.findById(tag.getId()).orElseThrow(() -> new DocumentNotFoundException("No tag found for id: " + tag.getId()));
		// slugs are unique, so changing the slug needs a sanity check
		// FIXME change the IllegalArgumentException to something more meaningful
		if (!existingTag.getSlug().equals(tag.getSlug()) && tagRepository.existsBySlug(tag.getSlug())) throw new IllegalArgumentException("The slug " + tag.getSlug() + " is already in use by another tag");
		existingTag.setName(tag.getName());
		existingTag.setSlug(tag.getSlug());
		existingTag = tagRepository.save(existingTag);
		eventPublisher.publishEvent(new TagLifecycleEvent(existingTag, TagLifecycleEvent.Type.UPDATED));
		return existingTag;
	}

	/**
	 * Deletes a tag from the database.
	 */
	public void deleteTag(String id) {
		logger.debug("Deleting Tag with id: {}", id);
		if (id == null || id.isEmpty()) throw new IllegalArgumentException("Id argument can't be null");
		
		Optional<Tag> existingTag = tagRepository.findById(id);
		if (existingTag.isEmpty()) return;

		tagRepository.deleteById(id);
		eventPublisher.publishEvent(new TagLifecycleEvent(existingTag.get(), TagLifecycleEvent.Type.DELETED));
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
