package com.example.pfe_project.repository.work_position;

import com.example.pfe_project.models.work_position.WorkPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;
public interface WorkPositionRepository extends JpaRepository<WorkPosition, Integer> {


    @Query(value = "select  wp from WorkPosition  wp where wp.id = :workPositionId")
    Optional<WorkPosition> fetchWorkPositionById(@Param("workPositionId")long workPositionId);

    @Query(value ="select wp from WorkPosition  wp")
    List<WorkPosition> fetchAllWorkPosition();

    @Modifying
    @Query(value = "delete from WorkPosition wp where wp.id = :workPositionId")
    void deleteWorkPositionById(@Param("workPositionId")long workPositionId);

}
