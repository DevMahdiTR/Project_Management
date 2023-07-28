package com.example.pfe_project.controllers.holiday;

import com.example.pfe_project.dto.holiday.HolidayTypeDto;
import com.example.pfe_project.dto.holiday.HolidayTypeUpdateRequestDto;
import com.example.pfe_project.models.holiday.HolidayType;
import com.example.pfe_project.service.holiday.HolidayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import  java.util.List;

@RequestMapping("api/v1/holiday_type")
@RestController
public class HolidayTypeController {

    private final HolidayTypeService holidayTypeService;
    @Autowired
    public HolidayTypeController(HolidayTypeService holidayTypeService)
    {
        this.holidayTypeService = holidayTypeService;
    }


    @PostMapping("/admin/create")
    public ResponseEntity<String> createHolidayType(@Valid @RequestBody HolidayType holidayType)
    {
        return holidayTypeService.createHolidayType(holidayType);
    }

    @GetMapping("/all/get/all")
    public ResponseEntity<List<HolidayTypeDto>> getAllHolidayTypes()
    {
        return holidayTypeService.getAllHolidayTypes();
    }


    @PutMapping("/admin/update/id/{holidayId}")
    public ResponseEntity<String> modifyHolidayTypeById(@PathVariable("holidayId") long holidayId,@RequestBody HolidayTypeUpdateRequestDto holidayTypeUpdateDeleteRequestDto)
    {
        return holidayTypeService.modifyHolidayTypeById(holidayId,holidayTypeUpdateDeleteRequestDto);
    }

    @PutMapping("/admin/update/name/{holidayName}")
    public ResponseEntity<String> modifyHolidayTypeByName(@PathVariable("holidayName") String holidayName,@RequestBody HolidayTypeUpdateRequestDto holidayTypeUpdateDeleteRequestDto)
    {
        return holidayTypeService.modifyHolidayTypeByName(holidayName,holidayTypeUpdateDeleteRequestDto);
    }


    @DeleteMapping("/admin/delete/id/{holidayId}")
    public ResponseEntity<String> deleteHolidayTypeById(@PathVariable("holidayId") long holidayId)
    {
        return holidayTypeService.deleteHolidayTypeById(holidayId);
    }


    @DeleteMapping("/admin/delete/name/{holidayName}")
    public ResponseEntity<String> deleteHolidayTypeByName(@PathVariable("holidayName") String holidayName)
    {
        return holidayTypeService.deleteHolidayTypeByName(holidayName);
    }


}
