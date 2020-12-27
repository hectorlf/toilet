package com.hectorlopezfernandez.blog.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implements the logic to handle /tags
 */
@Service
public class TagsService {

	private static final Logger logger = LoggerFactory.getLogger(TagsService.class);

	private final TagRepository tagRepository;

	@Inject
	public TagsService(TagRepository tagRepository) {
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
	 * Returns a collection of Tags identified by their ids
	 */
	public Collection<Tag> getTags(Collection<String> ids) {
		logger.debug("Going into .getTags()");
		if (ids == null || ids.isEmpty()) return Collections.emptyList();
		Collection<Tag> tags = new ArrayList<>(ids.size());
		// FIXME this will bypass caches
		tagRepository.findAllById(ids).forEach(t -> tags.add(t));
		return tags;
	}

	/**
	 * Returns a Tag identified by its slug, if it exists
	 */
	public Optional<Tag> getTagBySlug(String slug) {
		logger.debug("Going into .getTagBySlug()");
		if (slug == null || slug.isBlank()) return Optional.empty();
		return tagRepository.findBySlug(slug);
	}

	/**
	 * Returns a collection of Tags identified by their slugs
	 */
	public Collection<Tag> getTagsBySlug(Collection<String> slugs) {
		logger.debug("Going into .getTagsBySlug()");
		if (slugs == null || slugs.isEmpty()) return Collections.emptyList();
		Collection<Tag> tags = new ArrayList<>();
		for (String slug : slugs) {
			Optional<Tag> tag = tagRepository.findBySlug(slug);
			if (tag.isPresent()) tags.add(tag.get());
		}
		return tags;
	}

}
