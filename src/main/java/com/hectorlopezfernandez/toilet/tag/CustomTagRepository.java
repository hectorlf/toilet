package com.hectorlopezfernandez.toilet.tag;

import java.util.List;
import java.util.Optional;

public interface CustomTagRepository {

	List<Tag> listTags(Optional<String> slug);

	void updateTagCount(String id);

}
