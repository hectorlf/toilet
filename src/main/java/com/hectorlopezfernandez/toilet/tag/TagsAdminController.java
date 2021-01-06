package com.hectorlopezfernandez.toilet.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Deals with the .page definitions in the admin UI
 * 
 * @author hector
 */
@Controller
public class TagsAdminController {

	private static final Logger logger = LoggerFactory.getLogger(TagsAdminController.class);

	@RequestMapping("/admin/tags.page")
	public String tagsPage() {
		logger.debug("Going into .tagsPage()");
		return "admin/pages/tags";
	}

	@RequestMapping("/admin/tag-new.page")
	public String newTagPage() {
		logger.debug("Going into .newTagPage()");
		return "admin/pages/tag-new";
	}

	@RequestMapping("/admin/tag-edit.page")
	public String editTagPage(@RequestParam("id") String id, ModelMap model) {
		logger.debug("Going into .editTagPage()");
		model.put("tagId", id);
		return "admin/pages/tag-edit";
	}

}
