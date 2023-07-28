package com.example.pfe_project.controllers.holiday;


import com.example.pfe_project.dto.holiday.HolidayRequestDto;
import com.example.pfe_project.service.holiday.HolidayRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/holiday_request")
@RestController
public class HolidayRequestController {

    private final HolidayRequestService holidayRequestService;

    @Autowired
    public HolidayRequestController(HolidayRequestService holidayRequestService)
    {
        this.holidayRequestService = holidayRequestService;
    }


    @PostMapping("/all/request_holiday")
    public ResponseEntity<String>  requestHoliday(@Valid @RequestBody HolidayRequestDto holidayRequestDto)
    {
        return holidayRequestService.requestHoliday(holidayRequestDto);
    }



    @PutMapping("/admin/accept/request/id/{holidayRequestId}")
    public ResponseEntity<String> acceptUserHolidayRequest(@PathVariable  long holidayRequestId)
    {
        return holidayRequestService.acceptUserHolidayRequest(holidayRequestId);
    }


    @PutMapping("/admin/reject/request/id/{holidayRequestId}")
    public ResponseEntity<String> rejectUserHolidayRequest(@PathVariable long holidayRequestId)
    {
        return holidayRequestService.rejectUserHolidayRequest(holidayRequestId);
    }

    @GetMapping("/admin/get/all/requests")
    public ResponseEntity<List<HolidayRequestDto>> fetchAllHolidayRequest()
    {
        return holidayRequestService.fetchAllHolidayRequest();
    }

    @GetMapping("/all/get/all/user_requests/id/{userId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchUserHolidayRequestsById(@PathVariable("userId") long userId)
    {
        return holidayRequestService.fetchUserHolidayRequestsById(userId);
    }

    @GetMapping("/all/get/accepted/user_requests/id/{userId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchAcceptedUserHolidayRequestsById(@PathVariable long userId)
    {
        return holidayRequestService.fetchAcceptedUserHolidayRequestsById(userId);
    }

    @GetMapping("/all/get/rejected/user_requests/id/{userId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchRejectedUserHolidayRequestsById(@PathVariable long userId)
    {
        return holidayRequestService.fetchRejectedUserHolidayRequestsById(userId);
    }

    @GetMapping("/all/get/pending/user_requests/id/{userId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchPendingUserHolidayRequestsById(@PathVariable long userId)
    {
        return holidayRequestService.fetchPendingUserHolidayRequestsById(userId);
    }


    @GetMapping("/admin/get/all/holiday_type_requests/id/{holidayTypeId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchHolidayTypeHolidayRequestsById(@PathVariable long holidayTypeId)
    {
        return holidayRequestService.fetchHolidayTypeHolidayRequestsById(holidayTypeId);
    }


    @GetMapping("/admin/get/accepted/holiday_type_requests/id/{holidayTypeId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchAcceptedHolidayTypeHolidayRequestsById(@PathVariable long holidayTypeId)
    {
        return holidayRequestService.fetchAcceptedHolidayTypeHolidayRequestsById(holidayTypeId);
    }
    @GetMapping("/admin/get/rejected/holiday_type_requests/id/{holidayTypeId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchRejectedHolidayTypeHolidayRequestsById(@PathVariable long holidayTypeId)
    {
        return holidayRequestService.fetchRejectedHolidayTypeHolidayRequestsById(holidayTypeId);
    }
    @GetMapping("/admin/get/pending/holiday_type_requests/id/{holidayTypeId}")
    public ResponseEntity<List<HolidayRequestDto>> fetchPendingHolidayTypeHolidayRequestsById(@PathVariable long holidayTypeId)
    {
        return holidayRequestService.fetchPendingHolidayTypeHolidayRequestsById(holidayTypeId);
    }
}
