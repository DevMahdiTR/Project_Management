package com.example.pfe_project.repository.user;

import com.example.pfe_project.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Query("SELECT U FROM UserEntity U WHERE U.username LIKE :prefix%")
    List<UserEntity> fetchUsersWithUsernamePrefix(@Param("prefix")String prefix);
    @Query("SELECT U FROM UserEntity U WHERE U.email LIKE :prefix%")
    List<UserEntity> fetchUsersWithEmailPrefix(@Param("prefix")String prefix);
    @Query("SELECT U FROM UserEntity U WHERE U.phoneNumber LIKE :prefix%")
    List<UserEntity> fetchUsersWithPhoneNumberPrefix(@Param("prefix")String prefix);




    @Query(value = "SELECT U FROM UserEntity U WHERE  U.username = :username ")
    Optional<UserEntity> fetchUserWithUsername(String username);
    @Query(value = "SELECT U FROM UserEntity  U WHERE U.id = :id")
    Optional<UserEntity> fetchUserWithId(long id);
    @Query(value = "SELECT U FROM UserEntity U WHERE  U.email = :email ")
    Optional<UserEntity> fetchUserWithEmail(String email);
    @Query(value = "SELECT U FROM UserEntity U WHERE U.phoneNumber = :phoneNumber")
    Optional<UserEntity> fetchUserByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.username = :username) AS RESULT")
    Boolean isUsernameRegistered(String username);
    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.email = :email) AS RESULT")
    Boolean isEmailRegistered(String email);
    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.phoneNumber = :phoneNumber) AS RESULT")
    Boolean isPhoneNumberRegistered(String phoneNumber);

}
