package com.Skillbox.AndrewBlog.repository;

import com.Skillbox.AndrewBlog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "order by posts.time desc", nativeQuery = true)
    List<Post> resentPost(Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts, post_votes " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.id = post_votes.post_id group by posts.id " +
            "order by count(post_votes.value) desc", nativeQuery = true)
    List<Post> bestPost(Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts, post_comments " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.id = post_comments.post_id group by posts.id " +
            "order by count(post_comments.user_id) desc", nativeQuery = true)
    List<Post> popularPost(Pageable pageable);

    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "order by posts.time asc", nativeQuery = true)
    List<Post> earlyPost(Pageable pageable);

}
