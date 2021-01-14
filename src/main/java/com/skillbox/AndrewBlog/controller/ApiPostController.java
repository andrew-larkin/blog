package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.api.request.CommentRequest;
import com.skillbox.AndrewBlog.api.request.ModerationRequest;
import com.skillbox.AndrewBlog.api.request.PostRequest;
import com.skillbox.AndrewBlog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiPostController {

	private final PostService postService;

	public ApiPostController(PostService postService) {
		this.postService = postService;
	}


	@GetMapping("/api/post")
	private ResponseEntity<?> getApiPost(@RequestParam int offset,
										 @RequestParam int limit,
										 @RequestParam String mode) {
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

	@GetMapping("/api/post/moderation")
	private ResponseEntity<?> getApiPostModeration(@RequestParam int offset,
											  @RequestParam int limit,
											  @RequestParam String status) {
		return postService.getApiPostModeration(offset, limit, status);
	}

	@GetMapping("/api/post/my")
	private ResponseEntity<?> getApiPostMy(@RequestParam int offset,
											  @RequestParam int limit,
											  @RequestParam String status) {
		return postService.getApiPostMy(offset, limit, status);
	}

	@GetMapping("/api/post/{ID}")
	private ResponseEntity<?> getApiPostId(@RequestParam int id) {
		return postService.getApiPostId(id);
	}


	@PostMapping("/api/post")
	private ResponseEntity<?> postApiPost(@RequestBody PostRequest postRequest) {
		return postService.postApiPost(postRequest);
	}

	@PutMapping("/api/post/{ID}")
	private ResponseEntity<?> putApiPost(@RequestParam int id,
										   @RequestBody PostRequest postRequest) {
		return postService.putApiPost(id, postRequest);
	}

	@PostMapping("/api/comment")
	private ResponseEntity<?> postApiComment(@RequestBody CommentRequest commentRequest) {
		return postService.postApiComment(commentRequest);
	}

	@PostMapping("/api/moderation")
	private ResponseEntity<?> postApiModeration(@RequestBody ModerationRequest moderationRequest) {
		return postService.postApiModeration(moderationRequest);
	}

	@PostMapping("/api/post/like")
	private ResponseEntity<?> postApiPostLike(@RequestParam int postId) {
		return postService.postApiPostLike(postId);
	}

	@PostMapping("/api/post/dislike")
	private ResponseEntity<?> postApiPostDislike(@RequestParam int postId) {
		return postService.postApiPostDislike(postId);
	}

}