package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.PostVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {

    @Query(value = "SELECT COUNT(*) FROM post_votes WHERE post_id = :post_id AND value > 0", nativeQuery = true)
    int getLikeCountByPostId (@Param("post_id") int postId);

    @Query(value = "SELECT COUNT(*) FROM post_votes WHERE post_id = :post_id AND value < 0", nativeQuery = true)
    int getDislikeCountByPostId (@Param("post_id") int postId);

}
