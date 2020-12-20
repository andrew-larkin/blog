package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {

    @Query(value = "select count(*) from tag2post where tag_id = :tag_id", nativeQuery = true)
    int getAmountOfTagByTagId(@Param("tag_id") int tagId);

}
