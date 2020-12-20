package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.CountPostsResponse;
import com.skillbox.AndrewBlog.api.response.ErrorDescriptionResponse;
import com.skillbox.AndrewBlog.api.response.IdNameResponse;
import com.skillbox.AndrewBlog.api.response.PostEntityResponse;
import com.skillbox.AndrewBlog.model.Post;
import com.skillbox.AndrewBlog.repository.PostCommentRepository;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.PostVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private PostVoteRepository postVoteRepository;
    private PostCommentRepository postCommentRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostVoteRepository postVoteRepository,
                       PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public ResponseEntity<?> getApiPosts(int offset, int limit, String mode) {

        StringBuilder errors = new StringBuilder();

        if (offset <= 0) {
            errors.append("'offset' should be greater than 0. ");
        }
        if (limit <= 0) {
            errors.append("'limit' should be more than 0. ");
        }
        if (!mode.equals("recent") || !mode.equals("popular") || !mode.equals("best") || !mode.equals("early")) {
            errors.append("'mode' ").append(mode).append(" is not defined");
        }
        if (!errors.toString().equals("")) {
            return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
        }

        Pageable pageable = PageRequest.of(offset, limit);

        List<Post> postList;
        switch (mode) {
            case "recent":
                postList = postRepository.resentPost(pageable);
                break;
            case "best":
                postList = postRepository.bestPost(pageable);
                break;
            case "popular":
                postList = postRepository.popularPost(pageable);
                break;
            case "early":
                postList = postRepository.earlyPost(pageable);
                break;
            default:
                postList = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)
        ));
    }

    private List<PostEntityResponse> getPostResponseListByPosts(List<Post> posts) {
        List<PostEntityResponse> postEntityResponseList = new ArrayList<>();
        for (Post post : posts) {
            postEntityResponseList.add(getPostEntityResponseByPost(post));
        }
        return postEntityResponseList;
    }

    private PostEntityResponse getPostEntityResponseByPost(Post post) {
        return new PostEntityResponse(
                post.getId(),
                post.getTime().getTime(),
                getIdNameResponseByPost(post),
                post.getTitle(),
                getAnnounceByPost(post),
                postVoteRepository.getLikeCountByPostId(post.getId()),
                postVoteRepository.getDislikeCountByPostId(post.getId()),
                postCommentRepository.getCommentCountByPostId(post.getId()),
                post.getViewCount()
        );
    }

    private IdNameResponse getIdNameResponseByPost(Post post) {
        return new IdNameResponse(
                post.getUser().getId(),
                post.getUser().getName()
        );
    }

    private String getAnnounceByPost(Post post) {
        if (post.getText().length() <= 150) {
            return post.getText();
        } else {
            return post.getText().substring(0, 149);
        }
    }

}