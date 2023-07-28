package com.example.pfe_project.repository.training_session;

import com.example.pfe_project.models.training_session.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
public interface TrainSessionRepository extends JpaRepository<TrainingSession,Integer> {



    @Query("SELECT T FROM TrainingSession T WHERE T.name LIKE :prefix%")
    List<TrainingSession> fetchTrainingSessionWithNamePrefix(String prefix);

    @Query(value = "SELECT T FROM TrainingSession T WHERE T.id = :id")
    Optional<TrainingSession> fetchTrainingSessionById(long id);

    @Query(value = "SELECT T FROM TrainingSession T WHERE T.name = :name")
    Optional<TrainingSession> fetchTrainingSessionByName(String name);

    @Query(value = "SELECT T FROM TrainingSession T")
    List<TrainingSession> fetchAllTrainingSessions();

    @Modifying
    @Query(value = "DELETE FROM TrainingSession T WHERE T.id = :trainSessionId")
    void deleteTrainingSessionById(@Param("trainSessionId") long trainSessionId);

    @Modifying
    @Query(value = "DELETE FROM TrainingSession T WHERE T.name = :trainSessionName")
    void deleteTrainingSessionByName(@Param("trainSessionName") String trainSessionName);




}
