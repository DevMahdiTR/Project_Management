package com.example.pfe_project.repository.holiday;

import com.example.pfe_project.models.holiday.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface HolidayTypeRepository extends JpaRepository<HolidayType,Integer> {


    @Query(value = "SELECT h FROM HolidayType h WHERE h.id = :holidayId")
    public Optional<HolidayType> fetchHolidayTypeById(@Param("holidayId") long holidayId);

    @Query(value = "SELECT h FROM HolidayType h WHERE h.holidayName = :holidayName")
    public Optional<HolidayType> fetchHolidayTypeByName(@Param("holidayName") String holidayName);



    @Query(value = "SELECT h FROM HolidayType h")
    public List<HolidayType> fetchAllHolidayTypes();


    @Modifying
    @Query(value = "DELETE FROM HolidayType h WHERE h.id = :holidayId")
    void deleteHolidayTypeById(@Param("holidayId") long holidayId);

    @Modifying
    @Query(value = "DELETE FROM HolidayType h WHERE h.holidayName = :holidayName")
    void deleteHolidayTypeByName(@Param("holidayName") String holidayName);
}
