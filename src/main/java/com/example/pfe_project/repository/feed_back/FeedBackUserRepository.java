package com.example.pfe_project.repository.feed_back;

import com.example.pfe_project.models.feedBack.FeedBackUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface FeedBackUserRepository extends JpaRepository<FeedBackUser, Integer> {



    @Query(value = "select fbu from FeedBackUser fbu where fbu.feedBack.id = :feedBackId")
    List<FeedBackUser> fetchAllCommentInFormById(@Param("feedBackId") final long feedBackId);

    @Query(value = "select fbu from FeedBackUser fbu where fbu.feedBack.id = :feedBackId and fbu.userEntity.id = :userId")
    List<FeedBackUser> fetchAllCommentOfUserInForm(@Param("feedBackId") final long feedBackId, @Param("userId") final long userId);


    @Query(value = "select  tbu from FeedBackUser  tbu where tbu.id = :feedBackUserId")
    Optional<FeedBackUser>  fetchFeedBackUserById(@Param("feedBackUserId") final long feedBackUserId);


    @Modifying
    @Query(value = "delete from FeedBackUser fbu where fbu.id = :feedBackUserId")
    void deleteCommentById(@Param("feedBackUserId") long feedBackUserId);


    @Modifying
    @Query(value ="delete from FeedBackUser fbu where fbu.feedBack.id = :feedBackId")
    void deleteAllCommentRelatedToForm(final long feedBackId);
}
