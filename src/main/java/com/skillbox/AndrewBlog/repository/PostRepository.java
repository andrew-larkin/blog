package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() order by posts.time desc", nativeQuery = true)
    List<Post> resentPost(Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts, post_votes " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' and post_votes.value > 0 " +
            "and posts.id = post_votes.post_id group by posts.id and posts.time<now()" +
            "order by count(post_votes.value) desc", nativeQuery = true)
    List<Post> bestPost(Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts, post_comments " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.id = post_comments.post_id and posts.time<now() group by posts.id " +
            "order by count(post_comments.user_id) desc", nativeQuery = true)
    List<Post> popularPost(Pageable pageable);

    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() order by posts.time asc", nativeQuery = true)
    List<Post> earlyPost(Pageable pageable);

    @Query(value = "select count(*) from posts", nativeQuery = true)
    int getAmountOfPosts();

    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() and posts.id = :id", nativeQuery = true)
    Optional<Post> getPostById(@Param("id") int id);


    List<Post> findByTextContainingAllIgnoreCaseAndIsActiveIsGreaterThanEqualAndModerationStatusEqualsAndTimeBefore(String query,
                                                                                                        byte isActive,
                                                                                  String isAccepted, Date now,
                                                                                  Pageable pageable);
    @Query(value = "select * from posts where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() and year(time) = :year and month(time) = :month and dayofmonth(time) = :day", nativeQuery = true)
    List<Post> getPostsByTime(@Param("year") int year, @Param("month") int month, @Param("day") int day, Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts " +
            "INNER JOIN tag2post ON posts.id = tag2post.post_id " +
            "left join tags on tag2post.tag_id = tags.id " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' and tags.name = :tag " +
            "and posts.time<now() GROUP BY posts.id", nativeQuery = true)
    List<Post> getPostsByTag(@Param("tag") String tag, Pageable pageable);

   /* int countByTitleAndIsActiveAndModerationStatusAndBetweenAndTimeIsBefore(byte isActive,
                   ModerationStatus moderationStatus, Date dateFrom, Date dateTo, Date now);
*/
    @Query(value = "select year(posts.time) from posts " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() group by year(posts.time)", nativeQuery = true)
    List<Integer> getYears();

    @Query(value = "SELECT date(time) from posts " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() group by date(time) order by date(time)", nativeQuery = true)
    List<Date> getDates();

    @Query(value = "SELECT count(date(time)) from posts " +
            "where date(time) = date(:time) and posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() group by date(time)", nativeQuery = true)
    Integer getDatesCount(@Param("time") Date date);

    @Query(value = "SELECT date(time) from posts where year(time) = :time " +
            "and posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() group by date(time) order by date(time)", nativeQuery = true)
    List<Date> getDatesByYear(@Param("time") String year);

    Integer countIdByModerationStatus(ModerationStatus moderationStatus);

}
