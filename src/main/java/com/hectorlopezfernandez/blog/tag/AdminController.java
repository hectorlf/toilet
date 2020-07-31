package com.hectorlopezfernandez.blog.tag;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private TagService tagService;

	@RequestMapping("/tags.page")
	public String tags(ModelMap model) {
		logger.debug("Going into Tag's AdminController.tags()");
		return "admin/tags";
	}

	@RequestMapping(path = "/api/tags", method = GET)
	@ResponseBody
	public List<Tag> listTags() {
		logger.debug("Going into Tag's AdminController.listTags()");
		return tagService.listTags();
	}

	@RequestMapping(path = "/api/tags/{tagId}", method = GET)
	public ResponseEntity<Tag> getTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.getTag()");
		Optional<Tag> tag = tagService.getTag(tagId);
		return tag.isPresent() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tag.get());
	}

}
