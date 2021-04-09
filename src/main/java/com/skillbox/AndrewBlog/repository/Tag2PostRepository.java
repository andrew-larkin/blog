package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {

    @Query(value = "select count(*) from tag2post where tag_id = :tag_id", nativeQuery = true)
    int getAmountOfTagByTagId(@Param("tag_id") int tagId);

    @Query(value = "select tag_id from tag2post where post_id = :post_id", nativeQuery = true)
    List<Integer> getTagIdByPostId(@Param("post_id") int postId);

    List<Tag2Post> findByPostId(int postId);

    @Query(value = "select count(*) from tag2post group by (tag_id) limit 1", nativeQuery = true)
    int getMaxTag();

    @Query(value = "select tags.name from tags, tag2post " +
            "where tags.id = tag2post.tag_id " +
            "group by tags.id", nativeQuery = true)
    List<String> getUsableTags();

    Optional<Tag2Post> findByPostIdAndTagId(int postId, int tagId);
}
