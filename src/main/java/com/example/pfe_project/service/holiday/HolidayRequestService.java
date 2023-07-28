package com.example.pfe_project.service.holiday;


import com.example.pfe_project.dto.holiday.HolidayRequestDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.holiday.HolidayRequest;
import com.example.pfe_project.models.holiday.HolidayType;
import com.example.pfe_project.models.training_session.EnrollmentStatus;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.holiday.HolidayRequestRepository;
import com.example.pfe_project.repository.holiday.HolidayTypeRepository;
import com.example.pfe_project.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HolidayRequestService {

    private final UserRepository userRepository;
    private final HolidayTypeRepository holidayTypeRepository;
    private final HolidayRequestRepository holidayRequestRepository;

    @Autowired
    public HolidayRequestService(UserRepository userRepository, HolidayTypeRepository holidayTypeRepository, HolidayRequestRepository holidayRequestRepository)
    {
        this.userRepository = userRepository;
        this.holidayTypeRepository = holidayTypeRepository;
        this.holidayRequestRepository = holidayRequestRepository;
    }


    public ResponseEntity<String> requestHoliday(@NotNull HolidayRequestDto holidayRequestDto)
    {
        UserEntity userEntity = getUserById(holidayRequestDto.getUserId());
        HolidayType holidayType = getHolidayTypeById(holidayRequestDto.getHolidayTypeId());

        if(!isVacationPeriodValid(userEntity,holidayRequestDto)){
            final String response = "The requested vacation duration exceeds the available entitlement.";
            return new ResponseEntity<String>(response,HttpStatus.BAD_REQUEST);
        }


        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setHolidayType(holidayType);
        holidayRequest.setUserEntity(userEntity);
        holidayRequest.setDescription(holidayRequestDto.getDescription());
        holidayRequest.setStartingDate(holidayRequestDto.getStatingDate());
        holidayRequest.setEndingDate(holidayRequestDto.getEndingDate());
        holidayRequest.setRequestStatus(EnrollmentStatus.PENDING);

        holidayType.getHolidayRequestList().add(holidayRequest);
        holidayTypeRepository.save(holidayType);


        final String successResponse = "The holiday request has been successfully submitted.";
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<String> acceptUserHolidayRequest(final long holidayRequestId) {
        HolidayRequest holidayRequest = getHolidayRequestById(holidayRequestId);
        UserEntity userEntity = getUserById(holidayRequest.getUserEntity().getId());

        userEntity.setSoldVacation(userEntity.getSoldVacation() - calculateDaysFromDates(new HolidayRequestDto(holidayRequest)));
        userRepository.save(userEntity);

        return updateHolidayRequestStatus(holidayRequestId, EnrollmentStatus.ACCEPTED, "accepted");
    }

    public ResponseEntity<String> rejectUserHolidayRequest(final long holidayRequestId) {
        return updateHolidayRequestStatus(holidayRequestId, EnrollmentStatus.REJECTED, "rejected");
    }




    public ResponseEntity<List<HolidayRequestDto>> fetchAllHolidayRequest()
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchAllHolidayRequest().stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchUserHolidayRequestsById(final long userId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchUserHolidayRequestsById(userId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchAcceptedUserHolidayRequestsById(final long userId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchAcceptedUserHolidayRequestsById(userId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchRejectedUserHolidayRequestsById(final long userId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchRejectedUserHolidayRequestsById(userId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchPendingUserHolidayRequestsById(final long userId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchPendingUserHolidayRequestsById(userId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }



    public ResponseEntity<List<HolidayRequestDto>> fetchHolidayTypeHolidayRequestsById(final long holidayTypeId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchHolidayTypeHolidayRequestsById(holidayTypeId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchAcceptedHolidayTypeHolidayRequestsById(final long holidayTypeId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchAcceptedHolidayTypeHolidayRequestsById(holidayTypeId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchRejectedHolidayTypeHolidayRequestsById(final long holidayTypeId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchRejectedHolidayTypeHolidayRequestsById(holidayTypeId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<HolidayRequestDto>> fetchPendingHolidayTypeHolidayRequestsById(final long holidayTypeId)
    {
        List<HolidayRequestDto> holidayRequestDtos = holidayRequestRepository.fetchPendingHolidayTypeHolidayRequestsById(holidayTypeId).stream().map(HolidayRequestDto::new).toList();
        return new ResponseEntity<>(holidayRequestDtos,HttpStatus.OK);
    }



    public UserEntity getUserById(final long userId) {
        return userRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d could not be found.", userId)));
    }
    public HolidayType getHolidayTypeById(final long holidayId)
    {
        return holidayTypeRepository.fetchHolidayTypeById(holidayId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The holiday with ID %d could not be found.", holidayId)));
    }

    public HolidayRequest getHolidayRequestById(final long holidayRequestId)
    {
        return holidayRequestRepository.fetchHolidayRequestById(holidayRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The holiday Request with ID %d could not be found.", holidayRequestId)));
    }



    private @NotNull ResponseEntity<String> updateHolidayRequestStatus(final long holidayRequestId, final EnrollmentStatus status, final String statusMessage) {
        HolidayRequest holidayRequest = getHolidayRequestById(holidayRequestId);

        holidayRequest.setRequestStatus(status);
        holidayRequestRepository.save(holidayRequest);

        final String successResponse = String.format("The holiday request of user with id: %d has been %s successfully.", holidayRequestId, statusMessage);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


    private long calculateDaysFromDates(final  @NotNull HolidayRequestDto holidayRequestDto)
    {
        long diffInMilliseconds = holidayRequestDto.getEndingDate().getTime() - holidayRequestDto.getStatingDate().getTime();
        return TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    }
    private boolean isVacationPeriodValid(final UserEntity userEntity,@NotNull final HolidayRequestDto holidayRequestDto)
    {
        return calculateDaysFromDates(holidayRequestDto) <= userEntity.getSoldVacation();
    }


}
