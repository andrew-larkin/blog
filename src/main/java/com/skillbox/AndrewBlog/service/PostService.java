package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.request.PostRequest;
import com.Skillbox.AndrewBlog.api.response.PostResponse;
import com.Skillbox.AndrewBlog.api.response.PostTempForResponse;
import com.Skillbox.AndrewBlog.api.response.UserForPostResponse;
import com.Skillbox.AndrewBlog.model.Post;
import com.Skillbox.AndrewBlog.model.PostComment;
import com.Skillbox.AndrewBlog.model.PostVote;
import com.Skillbox.AndrewBlog.repository.PostCommentRepository;
import com.Skillbox.AndrewBlog.repository.PostRepository;
import com.Skillbox.AndrewBlog.repository.PostVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements PostServiceInt {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostVoteRepository postVoteRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    public PostService(PostRepository postRepository, PostVoteRepository postVoteRepository,
                       PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public PostResponse getPosts(PostRequest postRequest) {
        PostResponse postResponse = new PostResponse(); //возвращаемый объект
        List<PostTempForResponse> postTempForResponseList = new ArrayList<>(); //массив для постов

        Pageable posts = PageRequest.of(postRequest.getOffset(),
                postRequest.getLimit());

        List<Post> postList;

        switch (postRequest.getMode()) {
            case "recent":
                postList = postRepository.resentPost(posts);
                break;
            case "best":
                postList = postRepository.bestPost(posts);
                break;
            case "popular":
                postList = postRepository.popularPost(posts);
                break;
            case "early":
                postList = postRepository.earlyPost(posts);
                break;
            default:
                throw new RuntimeException();
        }

        //получаем все оценки
        Iterable<PostVote> postVoteIterable = postVoteRepository.findAll();
        List<PostVote> postVoteList = new ArrayList<>();
        postVoteIterable.forEach(postVoteList::add);

        //получаем все комментарии
        Iterable<PostComment> postCommentIterable = postCommentRepository.findAll();
        List<PostComment> postCommentList = new ArrayList<>();
        postCommentIterable.forEach(postCommentList::add);

        //получаем и устанавливаем общее количество постов
        postResponse.setCount(postList.size());

        //на основании каждого поста создаем и заполняем поля для postTempForResponse
        for (int i = 0; i < postList.size(); i++) {

                PostTempForResponse postTempForResponse = new PostTempForResponse();
                UserForPostResponse userForPostResponse = new UserForPostResponse();

                int commentCount = 0;
                int likes = 0;
                int dislikes = 0;

                int postId = postList.get(i).getId();
                postTempForResponse.setId(postId);

                postTempForResponse.setTimestamp(postList.get(i).getTime().getTime());

                //заполняем поля для User и добавляем его в текущий экземпляр класса
                userForPostResponse.setId(postList.get(i).getUser().getId());
                userForPostResponse.setName(postList.get(i).getUser().getName());
                postTempForResponse.setUser(userForPostResponse);

                postTempForResponse.setTitle(postList.get(i).getTitle());

                postTempForResponse.setAnnounce(""); //пока что пустая аннотация

                //рассчитываем кол-во лайков и дизлайков
            for (PostVote postVote : postVoteList) {
                if (postVote.getPostId() == postId) {
                    if (postVoteList.get(i).getValue() > 0) {
                        likes++;
                    } else if (postVoteList.get(i).getValue() < 0) {
                        dislikes++;
                    }
                }
            }

                postTempForResponse.setLikeCount(likes);
                postTempForResponse.setDislikeCount(dislikes);

                //считаем количество комментариев к данному посту
            for (PostComment postComment : postCommentList) {
                if (postComment.getPostId() == postId) {
                    commentCount++;
                }
            }
                postTempForResponse.setCommentCount(commentCount);

                postTempForResponse.setViewCount(postList.get(i).getViewCount());

                //добавляем полностью заполненный postTempForResponse в список
                postTempForResponseList.add(postTempForResponse);
            }
        postResponse.setPosts(postTempForResponseList);
        return postResponse;
    }
}