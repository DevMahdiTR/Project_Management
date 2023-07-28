package com.example.pfe_project.repository.training_session;

import com.example.pfe_project.models.training_session.TrainingSessionUser;
import com.example.pfe_project.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionUserRepository extends JpaRepository<TrainingSessionUser,Integer> {


    @Query(value = "SELECT u FROM TrainingSessionUser tsu INNER JOIN UserEntity u on tsu.userEntity.id = u.id where tsu.trainingSession.id = :trainSessionId and tsu.enrollmentStatus = 'PENDING'")
    List<UserEntity> getEnrollmentRequests(@Param("trainSessionId") long trainSessionId);
    @Query(value = "SELECT u FROM TrainingSessionUser tsu INNER JOIN UserEntity u on tsu.userEntity.id = u.id where tsu.trainingSession.id = :trainSessionId and tsu.enrollmentStatus = 'ACCEPTED'")
    List<UserEntity> getEnrollmentUserAccepted(@Param("trainSessionId") long trainSessionId);
    @Query(value = "SELECT u FROM TrainingSessionUser tsu INNER JOIN UserEntity u on tsu.userEntity.id = u.id where tsu.trainingSession.id = :trainSessionId and tsu.enrollmentStatus = 'REJECTED'")
    List<UserEntity> getEnrollmentUserRejected(@Param("trainSessionId") long trainSessionId);
    @Query(value = "SELECT u FROM TrainingSessionUser tsu INNER JOIN UserEntity u on tsu.userEntity.id = u.id where tsu.trainingSession.id = :trainSessionId")
    List<UserEntity> getAllEnrollmentUserById(@Param("trainSessionId") long trainSessionId);
    @Query(value = "SELECT u FROM TrainingSessionUser tsu INNER JOIN UserEntity u on tsu.userEntity.id = u.id where tsu.trainingSession.name = :trainSessionName")
    List<UserEntity> getAllEnrollmentUserByName(@Param("trainSessionName") String trainSessionName);


    @Query(value = "SELECT tsu FROM TrainingSessionUser tsu WHERE  tsu.trainingSession.id = :trainSessionId AND tsu.userEntity.id = :userId")
    Optional<TrainingSessionUser> fetchTrainingSessionUserUserIdTrainingSessionId(@Param("trainSessionId") long trainSessionId, @Param("userId") long userId);
    @Query(value = "SELECT EXISTS(SELECT tsu FROM TrainingSessionUser tsu WHERE  tsu.trainingSession.id = :trainSessionId AND tsu.userEntity.id = :userId) AS RESULT")
    Boolean isUserEnrolledInTrainingSession(@Param("trainSessionId") long trainSessionId,@Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM TrainingSessionUser tsu WHERE tsu.trainingSession.id = :trainSessionId AND tsu.userEntity.id = :userId")
    void removeEnrollmentForUserById(@Param("trainSessionId") long trainSessionId,@Param("userId") long userId);


    @Modifying
    @Query(value = "DELETE FROM TrainingSessionUser tsu WHERE tsu.trainingSession.id = :trainSessionId")
    void removeEnrollmentForUserRelatedToTrainingSessionById(@Param("trainSessionId") long trainSessionId);


}
