package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select * from users where users.id = :user_id", nativeQuery = true)
    User getUserByComment(@Param("user_id") long userId);

    Optional<User> getUserByEmail(String email);
}
