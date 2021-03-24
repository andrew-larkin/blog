package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.request.CommentRequest;
import com.skillbox.AndrewBlog.api.request.LikeRequest;
import com.skillbox.AndrewBlog.api.request.ModerationRequest;
import com.skillbox.AndrewBlog.api.request.PostRequest;
import com.skillbox.AndrewBlog.api.response.*;
import com.skillbox.AndrewBlog.model.*;
import com.skillbox.AndrewBlog.repository.*;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final byte isActive = 1;

    private PostRepository postRepository;
    private PostVoteRepository postVoteRepository;
    private PostCommentRepository postCommentRepository;
    private UserRepository userRepository;
    private Tag2PostRepository tag2PostRepository;
    private TagsRepository tagsRepository;
    private PersonDetailsService personDetailsService;

    @Autowired
    public PostService(PostRepository postRepository, PostVoteRepository postVoteRepository,
                       PostCommentRepository postCommentRepository, UserRepository userRepository,
                       Tag2PostRepository tag2PostRepository, TagsRepository tagsRepository,
                       PersonDetailsService personDetailsService) {
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.postCommentRepository = postCommentRepository;
        this.userRepository = userRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.tagsRepository = tagsRepository;
        this.personDetailsService = personDetailsService;
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
                .getPostsByTime(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]),
                        PageRequest.of(offset, limit));

        return ResponseEntity.status(200).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)));
    }

    public ResponseEntity<?> getApiPostByTag(int offset, int limit, String tag) {

        StringBuilder errors = new StringBuilder();
        if (offset < 0) {
            errors.append("'offset' should be equal or greater than 0. ");
        }
        if (limit <= 0) {
            errors.append("'limit' should be equal or greater than 0. ");
        }
        if (tagsRepository.getTagByName(tag).isEmpty()) {
            errors.append("defined 'tag' is not found. ");
        }
        if (!errors.toString().equals("")) {
            return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
        }

        List<Post> postList = postRepository.getPostsByTag(tag, PageRequest.of(offset, limit));

        return ResponseEntity.status(200).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)));
    }

    public ResponseEntity<?> getApiPostModeration(int offset, int limit, String status) {

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
        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        if (user.getIsModerator() != isActive) {
            return ResponseEntity.status(403).body("");
        }

        Pageable pageable = PageRequest.of(offset, limit);

        List<Post> postList;
        switch (status) {
            case "new":
                postList = postRepository.moderationNewPosts(pageable);
                break;
            case "declined":
                postList = postRepository.moderationDeclinedPosts(user.getId(), pageable);
                break;
            case "accepted":
                postList = postRepository.moderationAcceptedPosts(user.getId(), pageable);
                break;
            default:
                postList = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)
        ));

    }

    public ResponseEntity<?> getApiPostMy(int offset, int limit, String status) {

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

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        Pageable pageable = PageRequest.of(offset, limit);

        List<Post> postList;
        switch (status) {
            case "inactive":
                postList = postRepository.myInactivePosts(user.getId(), pageable);
                break;
            case "pending":
                postList = postRepository.myPendingPosts(user.getId(), pageable);
                break;
            case "declined":
                postList = postRepository.myDeclinedPosts(user.getId(), pageable);
                break;
            case "published":
                postList = postRepository.myPublishedPosts(user.getId(), pageable);
                break;
            default:
                postList = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CountPostsResponse(
                postList.size(),
                getPostResponseListByPosts(postList)
        ));
    }

        public ResponseEntity<?> getApiPostId(int id) {

        Optional<Post> optionalPost = postRepository.getPostById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post with id '" + id + "' is not found.");
        }

        //после наладки авторизации добавить логику для автора и модератора
        int moderatorId = 1456;
        int userId = 1456;
        if (!(optionalPost.get().getModeratorId() == moderatorId)
                || !(optionalPost.get().getUser().getId() == userId)) {
            int viewCount = optionalPost.get().getViewCount();
            viewCount++;
            optionalPost.get().setViewCount(viewCount);
            postRepository.saveAndFlush(optionalPost.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new PostSingleEntityResponse(
                optionalPost.get().getId(),
                optionalPost.get().getTime().getTime()/1000,
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
    }

    public ResponseEntity<?> postApiPost(PostRequest postRequest) {

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found",
                                personDetailsService.getCurrentUser().getEmail()))
        );

        Map<String, String> errors = new HashMap<>();
        if (postRequest.getTitle().length() < 3) {
            errors.put("title: ", "title of the post can not be empty and should contain more then 3 symbols ");
        }
        if (postRequest.getText().length() < 50) {
            errors.put("text: ", "text of the post can not be empty and should contain more then 50 symbols ");
        }
        if (postRepository.findByTitle(postRequest.getTitle()).isPresent()) {
            errors.put("post: ", "post with the same title already exists ");
        }

        if (errors.size() != 0) {
            return ResponseEntity.status(200).body(new ResultErrorsResponse(
                    false,
                    errors));
        }

        Post post = new Post(
            isActive,
            user.getIsModerator() == isActive ? ModerationStatus.ACCEPTED : ModerationStatus.NEW,
            user.getIsModerator() == isActive ? user.getId() : 0,
            user,
            postRequest.getTimestamp() < System.currentTimeMillis() ? new Date(System.currentTimeMillis())
                    : new Date(postRequest.getTimestamp()),
            postRequest.getTitle(),
            postRequest.getText(),
            0
        );

        Post newPost = postRepository.save(post);

        for (int i = 0; i < postRequest.getTags().size(); i++) {
            if (tagsRepository.getTagByName(postRequest.getTags().get(i)).isEmpty()) {
                Tag newTag = tagsRepository.save(new Tag(postRequest.getTags().get(i)));
                tag2PostRepository.save(new Tag2Post(newPost,
                        newTag));
            } else {
                Tag existedTag = tagsRepository.getTagByName(postRequest.getTags().get(i)).get(0);
                tag2PostRepository.save(new Tag2Post(newPost,
                        existedTag));
            }
        }

        return ResponseEntity.status(200).body(new ResultResponse(
                true
        ));
    }

    public ResponseEntity<?> putApiPost(int id, PostRequest postRequest) {
        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        Map<String, String> errors = new HashMap<>();
        if (postRequest.getTitle().length() < 3) {
            errors.put("title: ", "title of the post can not be empty and should contain more then 3 symbols ");
        }
        if (postRequest.getText().length() < 50) {
            errors.put("text: ", "text of the post can not be empty and should contain more then 50 symbols ");
        }
        if (postRepository.findById(id).isEmpty()) {
            errors.put("post: ", "post is not found ");
        }

        if (postRepository.findById(id).get().getUser().getId() != user.getId()) {
            if (userRepository.findByEmail(user.getEmail()).get().getIsModerator() != isActive) {
                errors.put("user: ", "you do not have the rights to update this post ");
        }

        }

        if (errors.size() != 0) {
            return ResponseEntity.status(200).body(new ResultErrorsResponse(
                    false,
                    errors));
        }

        Post postToUpdate = postRepository.findById(id).orElseThrow(() ->
               new RuntimeException("post with actual ID is not found")
        );

        postToUpdate.setModerationStatus(postRepository.findById(id).get().getUser().getId() == user.getId()
                ? ModerationStatus.NEW : postToUpdate.getModerationStatus());
        postToUpdate.setTime(postRequest.getTimestamp() < System.currentTimeMillis()
                ? new Date(System.currentTimeMillis())
                : new Date(postRequest.getTimestamp()));
        postToUpdate.setTitle(postRequest.getTitle());
        postToUpdate.setText(postRequest.getText());

        postRepository.save(postToUpdate);

        Set<String> oldTags = tag2PostRepository.findByPostId(id).stream()
                .map(n -> n.getTag().getName())
                .collect(Collectors.toSet());

        for (int i = 0; i < postRequest.getTags().size(); i++) {
            if (!oldTags.contains(postRequest.getTags().get(i))) {
                Tag existedTag = tagsRepository.getTagByName(postRequest.getTags().get(i)).get(0);
                tag2PostRepository.save(new Tag2Post(postToUpdate,
                        existedTag));
            }
        }

        return ResponseEntity.status(200).body(new ResultResponse(
                true
        ));
    }

    public ResponseEntity<?> postApiComment(CommentRequest commentRequest) {

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        Map<String, String> errors = new HashMap<>();
        if (commentRequest.getParentId() != 0) {
            if (postCommentRepository
                    .findById(commentRequest.getParentId()).isEmpty()) {
                errors.put("parent_id: ", "No such parent_id");
        }
        }
        if (postRepository.findById(commentRequest.getPostId()).isEmpty()) {
            errors.put("post: ", "post is not found");
        }
        if (commentRequest.getText().length() < 3) {
            errors.put("text: ", "text of the comment should contain more 2 symbols");
        }

        if (errors.size() != 0) {
            return ResponseEntity.status(400).body(new ResultErrorsResponse(
                    false,
                    errors));
        }

        PostComment newComment = postCommentRepository.save(new PostComment(
                commentRequest.getParentId(),
                postRepository.findById(commentRequest.getPostId()).get(),
                userRepository.findByEmail(user.getEmail()).get(),
                new Date(System.currentTimeMillis()),
                commentRequest.getText()
        ));

        return ResponseEntity.status(200).body(new IdResponse(
                newComment.getId()
        ));
    }

    public ResponseEntity<?> postApiModeration(ModerationRequest moderationRequest) {

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        if (user.getIsModerator() != isActive) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }

        Post post = postRepository.findById(moderationRequest.getPostId()).get();
        post.setModeratorId(user.getId());
        post.setModerationStatus(
                moderationRequest.getDecision().equals("accept")
                        ? ModerationStatus.ACCEPTED : ModerationStatus.DECLINED);

        postRepository.save(post);

        return ResponseEntity.status(200).body(new ResultResponse(
                true
        ));
    }

    public ResponseEntity<?> postApiPostLike(LikeRequest likeRequest) {

        final byte LIKE = 1;

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        if (postVoteRepository.findByPostIdAndUserId(likeRequest.getPostId(), user.getId()).isEmpty()) {
            postVoteRepository.save(new PostVote(
                    user.getId(),
                    likeRequest.getPostId(),
                    new Date(System.currentTimeMillis()),
                    LIKE
            ));
            return ResponseEntity.status(200).body(new ResultResponse(true));
        } else if (postVoteRepository.findByPostIdAndUserId(likeRequest.getPostId(), user.getId()).get().getValue() != LIKE) {
            PostVote changePostVote = postVoteRepository.findByPostIdAndUserId(likeRequest.getPostId(), user.getId()).get();
            changePostVote.setValue(LIKE);
            postVoteRepository.save(changePostVote);
            return ResponseEntity.status(200).body(new ResultResponse(true));
        }
            return ResponseEntity.status(200).body(new ResultResponse(false));

    }

    public ResponseEntity<?> postApiPostDislike(int postId) {
        return ResponseEntity.status(200).body("заглушка");
    }

    private List<PostEntityResponse> getPostResponseListByPosts(List<Post> postList) {
        List<PostEntityResponse> posts = new ArrayList<>();
        for (Post post : postList) {
            posts.add(getPostEntityResponseByPost(post));
        }
        return posts;
    }

    private PostEntityResponse getPostEntityResponseByPost(Post post) {
        return new PostEntityResponse(
                post.getId(),
                post.getTime().getTime()/1000,
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
        IdNameResponse user = new IdNameResponse(post.getUser().getId(),
                post.getUser().getName());
        return user;
    }

    private List<CommentEntityResponse> getComments(Post post) {

        List<PostComment> comments = postCommentRepository.getPostCommentsByPostId(post.getId());
        if (comments.isEmpty()) {return new ArrayList<>();}
        List<CommentEntityResponse> commentEntityResponseList = new ArrayList<>();
        for (PostComment postComment : comments) {
            commentEntityResponseList.add(new CommentEntityResponse(
                    postComment.getId(),
                    postComment.getTime().getTime()/1000,
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

    private List<String> getTagsByPost(Post post) {
        List<String> tagsNameList = new ArrayList<>();
        List<Integer> tagsIdList = tag2PostRepository.getTagIdByPostId(post.getId());
        if (tagsIdList.isEmpty()) {return new ArrayList<>();}
        for (int tagId : tagsIdList) {
            tagsNameList.add(tagsRepository.findById(tagId).get().getName());
        }
        return tagsNameList;
    }

    private String getAnnounceByPost(Post post) {
        StringBuilder stringBuilder = new StringBuilder();
        if (post.getText().length() <= 150) {
            return post.getText();
        } else {
            return stringBuilder.append(post.getText().substring(0, 149)).append("...").toString();
        }
    }

}