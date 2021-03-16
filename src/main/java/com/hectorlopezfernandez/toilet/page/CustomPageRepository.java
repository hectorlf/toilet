package com.hectorlopezfernandez.toilet.page;

import java.util.List;
import java.util.Optional;

public interface CustomPageRepository {

	List<Page> findPagesFilteredBy(Optional<String> slug);

}
