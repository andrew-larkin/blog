package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {

    @Query(value = "SELECT COUNT(*) FROM post_comments WHERE post_id = :post_id", nativeQuery = true)
    int getCommentCountByPostId (@Param("post_id") int postId);

}
