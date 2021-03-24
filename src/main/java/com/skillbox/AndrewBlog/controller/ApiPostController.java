package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.api.request.CommentRequest;
import com.skillbox.AndrewBlog.api.request.LikeRequest;
import com.skillbox.AndrewBlog.api.request.ModerationRequest;
import com.skillbox.AndrewBlog.api.request.PostRequest;
import com.skillbox.AndrewBlog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiPostController {

	private final String USER = "ROLE_USER";
	private final String MODERATOR = "ROLE_MODERATOR";

	private final PostService postService;

	public ApiPostController(PostService postService) {
		this.postService = postService;
	}


	@GetMapping("/api/post")
	private ResponseEntity<?> getApiPost(@RequestParam(defaultValue = "0") int offset,
										 @RequestParam(defaultValue = "10") int limit,
										 @RequestParam(defaultValue = "recent") String mode) {
		return postService.getApiPosts(offset, limit, mode);
	}

	@GetMapping("/api/post/search")
	private ResponseEntity<?> getApiPostSearch(@RequestParam int offset,
											   @RequestParam int limit,
											   @RequestParam String query) {
		return postService.getApiPostSearch(offset, limit, query);
	}

	@GetMapping("/api/post/byDate")
	private ResponseEntity<?> getApiPostByDate(@RequestParam int offset,
											   @RequestParam int limit,
											   @RequestParam String date) {
		return postService.getApiPostByDate(offset, limit, date);
	}

	@GetMapping("/api/post/byTag")
	private ResponseEntity<?> getApiPostByTag(@RequestParam int offset,
											   @RequestParam int limit,
											   @RequestParam String tag) {
		return postService.getApiPostByTag(offset, limit, tag);
	}

	//@Secured("MODERATOR")
	@GetMapping("/api/post/moderation")
	private ResponseEntity<?> getApiPostModeration(@RequestParam int offset,
											  @RequestParam int limit,
											  @RequestParam String status) {
		return postService.getApiPostModeration(offset, limit, status);
	}

	//@Secured({"USER", "MODERATOR"})
	@GetMapping("/api/post/my")
	private ResponseEntity<?> getApiPostMy(@RequestParam int offset,
											  @RequestParam int limit,
											  @RequestParam String status) {
		return postService.getApiPostMy(offset, limit, status);
	}

	@GetMapping("/api/post/{id}")
	private ResponseEntity<?> getApiPostId(@PathVariable int id) {
		return postService.getApiPostId(id);
	}


	//@Secured("USER")
	@PostMapping("/api/post")
	private ResponseEntity<?> postApiPost(@RequestBody PostRequest postRequest) {
		return postService.postApiPost(postRequest);
	}

	//@Secured("USER")
	@PutMapping("/api/post/{id}")
	private ResponseEntity<?> putApiPost(@PathVariable int id,
										   @RequestBody PostRequest postRequest) {
		return postService.putApiPost(id, postRequest);
	}

	//@Secured("USER")
	@PostMapping("/api/comment")
	private ResponseEntity<?> postApiComment(@RequestBody CommentRequest commentRequest) {
		return postService.postApiComment(commentRequest);
	}

	//@Secured(MODERATOR)
	@PostMapping("/api/moderation")
	private ResponseEntity<?> postApiModeration(@RequestBody ModerationRequest moderationRequest) {
		return postService.postApiModeration(moderationRequest);
	}

	//@Secured(USER)
	@PostMapping("/api/post/like")
	private ResponseEntity<?> postApiPostLike(@RequestBody LikeRequest likeRequest) {
		return postService.postApiPostLike(likeRequest);
	}

	//@Secured(USER)
	@PostMapping("/api/post/dislike")
	private ResponseEntity<?> postApiPostDislike(@RequestParam int postId) {
		return postService.postApiPostDislike(postId);
	}

}