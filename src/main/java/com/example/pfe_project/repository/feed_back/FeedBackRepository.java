package com.example.pfe_project.repository.feed_back;

import com.example.pfe_project.models.feedBack.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {



    @Query(value = "select f from FeedBack f where  f.id = :feedBackId")
    Optional<FeedBack> fetchFeedBackById(@Param("feedBackId") long feedBackId);

    @Query(value = "select f from FeedBack f")
    List<FeedBack> fetchAllFeedBackFroms();

    @Modifying
    @Query(value = "delete from FeedBack f where f.id = :feedBackId")
    void deleteFeedBackById(@Param("feedBackId") long feedBackId);
}
