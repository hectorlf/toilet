package com.hectorlopezfernandez.blog.admin.post;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hectorlopezfernandez.blog.post.Post;

@Controller
@RequestMapping("/admin")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@RequestMapping("/posts.page")
	public String posts(ModelMap model) {
		logger.debug("Going into PostController.posts()");
		return "admin/posts";
	}

	@RequestMapping("/api/posts")
	@ResponseBody
	public List<Post> listPosts(ModelMap model) {
		logger.debug("Going into PostController.listPosts()");
		Post post1 = new Post();
		post1.setId("asdf");
		post1.setContent("blah!");
		return Arrays.asList(post1);
	}

}