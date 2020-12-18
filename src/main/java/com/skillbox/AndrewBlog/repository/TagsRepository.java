package com.Skillbox.AndrewBlog.repository;

import com.Skillbox.AndrewBlog.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends CrudRepository<Tag, Integer> {
}
