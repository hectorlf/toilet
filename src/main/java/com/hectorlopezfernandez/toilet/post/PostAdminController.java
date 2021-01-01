package com.hectorlopezfernandez.toilet.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostAdminController {

	private static final Logger logger = LoggerFactory.getLogger(PostAdminController.class);

	@Autowired
	private ArchiveService archiveService;

	@RequestMapping("/admin/posts.page")
	public String posts(ModelMap model) {
		logger.debug("Going into .posts()");
		return "admin/posts";
	}

	@RequestMapping("/admin/api/posts")
	@ResponseBody
	public Page<Post> listPosts() {
		logger.debug("Going into .listPosts()");
		return archiveService.listPosts(PageRequest.of(0, 10, Direction.DESC, "id"));
	}

	@RequestMapping("/admin/api/posts/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable("postId") String postId) {
		logger.debug("Going into .getPost()");
		//FIXME
//		Optional<Post> post = archiveService.getPost(postId);
//		return post.isPresent() ? ResponseEntity.notFound().build() : ResponseEntity.ok(post.get());
		return ResponseEntity.notFound().build();
	}

}