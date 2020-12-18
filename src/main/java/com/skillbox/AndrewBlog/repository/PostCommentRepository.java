package com.Skillbox.AndrewBlog.repository;

import com.Skillbox.AndrewBlog.model.PostComment;
import org.springframework.data.repository.CrudRepository;

public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
}
