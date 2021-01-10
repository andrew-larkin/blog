package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.*;
import com.skillbox.AndrewBlog.model.Post;
import com.skillbox.AndrewBlog.model.PostComment;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private byte isActive = 1;

    private PostRepository postRepository;
    private PostVoteRepository postVoteRepository;
    private PostCommentRepository postCommentRepository;
    private UserRepository userRepository;
    private Tag2PostRepository tag2PostRepository;
    private TagsRepository tagsRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostVoteRepository postVoteRepository,
                       PostCommentRepository postCommentRepository, UserRepository userRepository,
                       Tag2PostRepository tag2PostRepository, TagsRepository tagsRepository) {
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.postCommentRepository = postCommentRepository;
        this.userRepository = userRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.tagsRepository = tagsRepository;
    }

    public ResponseEntity<?> getApiPosts(int offset, int limit, String mode) {

        StringBuilder errors = new StringBuilder();
        if (offset < 0) {
            errors.append("'offset' should be equal or greater than 0. ");
        }
        if (limit <= 0) {
            errors.append("'limit' should be greater than 0. ");
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

    public ResponseEntity<?> getApiPostSearch (int offset, int limit, String query) {
        StringBuilder errors = new StringBuilder();
        if (offset < 0) {
            errors.append("'offset' should be equal or greater than 0. ");
        }
        if (limit <= 0) {
            errors.append("'limit' should be greater than 0. ");
        }
        if (!errors.toString().equals("")) {
            return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
        }

        if (query.equals("")) {
            getApiPosts(offset, limit, "recent");
        }

        List<Post> postList = postRepository
          .findByTextContainingAllIgnoreCaseAndIsActiveIsGreaterThanEqualAndModerationStatusEqualsAndTimeBefore(query,
                  isActive, "ACCEPTED", new Date(System.currentTimeMillis()), PageRequest.of(offset, limit));

        if(postList.isEmpty()) {
            List<PostEntityResponse> emptyList = new ArrayList<>();
            return ResponseEntity.status(200).body(new CountPostsResponse(
                    postList.size(),
                    emptyList
            ));
        } else {
        return ResponseEntity.status(200).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)
        )); }
    }

    public ResponseEntity<?> getApiPostByDate(int offset, int limit, String date) {

        StringBuilder errors = new StringBuilder();
        if (offset < 0) {
            errors.append("'offset' should be equal or greater than 0. ");
        }
        if (limit <= 0) {
            errors.append("'limit' should be greater than 0. ");
        }


        if (date.length() != 10 || !date.contains("-")) {
            errors.append("'date' format should be 'yyyy-MM-dd'. ");
        }
        String[] dates = date.split("-");
        LocalDate postDate = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));

        if (postDate.isAfter(LocalDate.now())) {
            errors.append("'date' should be before current time. ");
        }
        if (!errors.toString().equals("")) {
            return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
        }

        List<Post> postList = postRepository
                .getPostsByTime(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));

        return ResponseEntity.status(200).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)));
    }

        /*public ResponseEntity<?> getApiPostId(int id) {

        Optional<Post> optionalPost = postRepository.getPostById(id);
        if (optionalPost.isEmpty() || optionalPost.get().getTime().getTime() > System.currentTimeMillis()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post with id '" + id + "' is not found.");
        }

        int viewCount = optionalPost.get().getViewCount();
        viewCount++;
        optionalPost.get().setViewCount(viewCount);
        postRepository.saveAndFlush(optionalPost.get());

        //после наладки авторизации добавить логику для автора и модератора

        return ResponseEntity.status(HttpStatus.OK).body(new PostEntityWithCommentsAndTagsResponse(
                id,
                System.currentTimeMillis(),
                true,
                getIdNameResponseByPost(optionalPost.get()),
                optionalPost.get().getTitle(),
                optionalPost.get().getText(),
                postVoteRepository.getLikeCountByPostId(optionalPost.get().getId()),
                postVoteRepository.getDislikeCountByPostId(optionalPost.get().getId()),
                optionalPost.get().getViewCount(),
                getComments(optionalPost.get()),
                getTagsByPost(optionalPost.get())
        ));
    }*/

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

    private List<CommentEntityResponse> getComments(Post post) {

        List<PostComment> comments = postCommentRepository.getPostCommentsByPostId(post.getId());
        List<CommentEntityResponse> commentEntityResponseList = new ArrayList<>();
        for (PostComment postComment : comments) {
            commentEntityResponseList.add(new CommentEntityResponse(
                    postComment.getId(),
                    postComment.getTime().getTime(),
                    postComment.getText(),
                    getIdNamePhotoResponseByComment(postComment)
            ));
        }
        return commentEntityResponseList;
    }

    private IdNamePhotoResponse getIdNamePhotoResponseByComment(PostComment postComment) {
        User user = userRepository.getUserByComment(postComment.getUserId());
        return new IdNamePhotoResponse(
                user.getId(),
                user.getName(),
                user.getPhoto()
        );
    }

    private TagListResponse getTagsByPost(Post post) {
        List<String> tagsNameList = new ArrayList<>();
        List<Integer> tagsIdList = tag2PostRepository.getTagIdByPostId(post.getId());
        for (int tagId : tagsIdList) {
            tagsNameList.add(tagsRepository.findById(tagId).get().getName());
        }
        return new TagListResponse(tagsNameList);
    }

    private String getAnnounceByPost(Post post) {
        if (post.getText().length() <= 150) {
            return post.getText();
        } else {
            return post.getText().substring(0, 149);
        }
    }

}