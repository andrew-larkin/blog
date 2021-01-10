package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends CrudRepository<Tag, Integer> {

    @Query(value = "select * from tags", nativeQuery = true)
    List<Tag> getAllTags();

    @Query(value = "select * from tags where name = :name", nativeQuery = true)
    List<Tag> getTagByName(@Param("name") String name);

}
