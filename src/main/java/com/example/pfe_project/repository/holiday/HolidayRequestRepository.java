package com.example.pfe_project.repository.holiday;

import com.example.pfe_project.models.holiday.HolidayRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface HolidayRequestRepository extends JpaRepository<HolidayRequest, Integer> {


    @Query(value = "SELECT HR FROM HolidayRequest HR WHERE HR.id = :holidayRequestId")
    Optional<HolidayRequest> fetchHolidayRequestById(@Param("holidayRequestId") long holidayRequestId);

    @Query(value = "SELECT HR FROM HolidayRequest HR")
    List<HolidayRequest> fetchAllHolidayRequest();

    @Query(value = "SELECT HR FROM HolidayRequest  HR WHERE HR.userEntity.id = :userId")
    List<HolidayRequest> fetchUserHolidayRequestsById(@Param("userId") long userId);

    @Query(value = "SELECT HR FROM HolidayRequest HR WHERE HR.userEntity.id = :userId AND HR.requestStatus = 'ACCEPTED'")
    List<HolidayRequest> fetchAcceptedUserHolidayRequestsById(@Param("userId") long userId);

    @Query(value = "SELECT HR FROM HolidayRequest HR WHERE HR.userEntity.id = :userId AND HR.requestStatus = 'REJECTED'")
    List<HolidayRequest> fetchRejectedUserHolidayRequestsById(@Param("userId") long userId);

    @Query(value = "SELECT HR FROM HolidayRequest HR WHERE HR.userEntity.id = :userId AND HR.requestStatus = 'PENDING'")
    List<HolidayRequest> fetchPendingUserHolidayRequestsById(@Param("userId") long userId);



    @Query(value = "SELECT HR FROM HolidayRequest  HR WHERE HR.holidayType.id = :holidayTypeId")
    List<HolidayRequest> fetchHolidayTypeHolidayRequestsById(@Param("holidayTypeId") long holidayTypeId);

    @Query(value = "SELECT HR FROM HolidayRequest  HR WHERE HR.holidayType.id = :holidayTypeId AND HR.requestStatus = 'ACCEPTED'")
    List<HolidayRequest> fetchAcceptedHolidayTypeHolidayRequestsById(@Param("holidayTypeId") long holidayTypeId);

    @Query(value = "SELECT HR FROM HolidayRequest  HR WHERE HR.holidayType.id = :holidayTypeId AND HR.requestStatus = 'REJECTED'")
    List<HolidayRequest> fetchRejectedHolidayTypeHolidayRequestsById(@Param("holidayTypeId") long holidayTypeId);

    @Query(value = "SELECT HR FROM HolidayRequest  HR WHERE HR.holidayType.id = :holidayTypeId AND HR.requestStatus = 'PENDING'")
    List<HolidayRequest> fetchPendingHolidayTypeHolidayRequestsById(@Param("holidayTypeId") long holidayTypeId);







}
