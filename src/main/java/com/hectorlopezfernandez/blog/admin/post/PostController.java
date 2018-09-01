package com.hectorlopezfernandez.blog.admin.post;

import java.util.Optional;

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

import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

@Controller
@RequestMapping("/admin")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private ArchiveService archiveService;

	@RequestMapping("/posts.page")
	public String posts(ModelMap model) {
		logger.debug("Going into PostController.posts()");
		return "admin/posts";
	}

	@RequestMapping("/api/posts")
	@ResponseBody
	public Page<Post> listPosts() {
		logger.debug("Going into PostController.listPosts()");
		return archiveService.listPosts(new PageRequest(0, 10, Direction.DESC, "id"));
	}

	@RequestMapping("/api/posts/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable("postId") String postId) {
		logger.debug("Going into PostController.getPost()");
		Optional<Post> post = archiveService.getPost(postId);
		return post.isPresent() ? ResponseEntity.notFound().build() : ResponseEntity.ok(post.get());
	}

}