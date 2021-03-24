package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.CaptchaCode;
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
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' and posts.time<now()" +
            "group by posts.id " +
            "order by count(post_votes.value)", nativeQuery = true)
    List<Post> bestPost(Pageable pageable);

    @Query(value = "SELECT posts.id, posts.is_active, posts.moderation_status, posts.moderator_id, " +
            "posts.text, posts.time, posts.title, posts.user_id, posts.view_count FROM posts, post_comments " +
            "where posts.is_active=1 and posts.moderation_status='ACCEPTED' " +
            "and posts.time<now() group by posts.id " +
            "order by count(post_comments.user_id)", nativeQuery = true)
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

    @Query(value = "select sum(view_count) from posts", nativeQuery = true)
    int getAmountOfViewCounts();

    @Query(value = "select time from posts order by id limit 1", nativeQuery = true)
    Date getDateOfFirstPublication();

    @Query(value = "select year(time) from posts where is_active = 1 and " +
            "moderation_status = 'ACCEPTED' and time <= now() group by year(time) desc", nativeQuery = true)
    Optional<Integer> getYearsWithActivePosts();

    @Query(value = "select date(time) from posts where is_active = 1 and " +
            "moderation_status = 'ACCEPTED' and date(time) <= now() group by date(time)", nativeQuery = true)
    List<String> getDatesWithActivePosts();

    @Query(value = "select count(*) from posts where is_active = 1 and " +
            "moderation_status = 'ACCEPTED' and date(time) = :time", nativeQuery = true)
    Integer getAmountOfPostsByDate(@Param("time") String date);

    @Query(value = "select date(time) from posts where is_active = 1 and " +
            "moderation_status = 'ACCEPTED' and date(time) <= now() " +
            "and year(time) = :time group by date(time)", nativeQuery = true)
    List<String> getDatesWithActivePostsByYear(@Param("time") Integer year);


    Optional<Post> findByTitle(String title);

    @Query(value = "select * from posts where is_active = 0 and " +
            "user_id = :user_id" , nativeQuery = true)
    List<Post> myInactivePosts(@Param("user_id") int id, Pageable pageable);

    @Query(value = "select * from posts where is_active = 1 and " +
            "moderation_status = 'NEW' and user_id = :user_id", nativeQuery = true)
    List<Post> myPendingPosts(@Param("user_id") int id, Pageable pageable);

    @Query(value = "select * from posts where is_active = 1 and " +
            "moderation_status = 'DECLINED' and user_id = :user_id", nativeQuery = true)
    List<Post> myDeclinedPosts(@Param("user_id") int id, Pageable pageable);

    @Query(value = "select * from posts where is_active = 1 and " +
            "moderation_status = 'ACCEPTED' and user_id = :user_id", nativeQuery = true)
    List<Post> myPublishedPosts(@Param("user_id") int id, Pageable pageable);

    int countByUserId(int userId);

    @Query(value = "select sum(view_count) from posts where user_id = :user_id", nativeQuery = true)
    int countAllViews(@Param("user_id") int id);

    @Query(value = "select time from posts where user_id = :user_id order by id limit 1", nativeQuery = true)
    Date getMyDateOfFirstPublication(@Param("user_id") int id);

    @Query(value = "select * from posts where moderation_status = 'NEW' " +
            "and is_active = 1", nativeQuery = true)
    List<Post> moderationNewPosts(Pageable pageable);

    @Query(value = "select * from posts where moderation_status = 'DECLINED' " +
            "and is_active = 1 and moderator_id = :moderator_id", nativeQuery = true)
    List<Post> moderationDeclinedPosts(@Param("moderator_id") int moderator_id, Pageable pageable);

    @Query(value = "select * from posts where moderation_status = 'ACCEPTED' " +
            "and is_active = 1 and moderator_id = :moderator_id", nativeQuery = true)
    List<Post> moderationAcceptedPosts(@Param("moderator_id") int moderator_id, Pageable pageable);
}
