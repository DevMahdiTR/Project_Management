package com.example.pfe_project.controllers.training_session;


import com.example.pfe_project.dto.training_session.EnrollmentRequestDTO;
import com.example.pfe_project.dto.training_session.TrainingSessionDto;
import com.example.pfe_project.dto.user.UserEntityDto;
import com.example.pfe_project.models.training_session.TrainingSession;
import com.example.pfe_project.service.training_session.TrainSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/training-sessions")
public class TrainSessionController {


    private final TrainSessionService trainSessionService;

    @Autowired
    public TrainSessionController(TrainSessionService trainSessionService)
    {
        this.trainSessionService = trainSessionService;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createTrainingSessions(@Valid @RequestBody TrainingSession trainingSession)
    {
        return trainSessionService.createTrainingSessions(trainingSession);
    }

    @DeleteMapping("/admin/delete/id/{trainSessionId}")
    public ResponseEntity<String> deleteTrainingSessionById(@PathVariable long trainSessionId)
    {
        return trainSessionService.deleteTrainingSessionById(trainSessionId);
    }

    @DeleteMapping("/admin/delete/name/{trainSessionName}")
    public ResponseEntity<String> deleteTrainingSessionByName(@PathVariable String trainSessionName)
    {
        return trainSessionService.deleteTrainingSessionByName(trainSessionName);
    }


    @GetMapping("/all/get/id/{trainSessionId}")
    public ResponseEntity<TrainingSessionDto> fetchTrainingSessionWithId(@PathVariable long trainSessionId)
    {
        return trainSessionService.fetchTrainingSessionWithId(trainSessionId);
    }

    @GetMapping("/all/get/name/{trainSessionName}")
    public ResponseEntity<TrainingSessionDto> fetchTrainingSessionWithName(@PathVariable String trainSessionName)
    {
        return trainSessionService.fetchTrainingSessionWithName(trainSessionName);
    }


    @GetMapping("/all/get/enrolled-users/name/{trainSessionName}")
    public ResponseEntity<List<UserEntityDto>> getEnrolledUsersForTrainingSessionByName(@PathVariable String trainSessionName)
    {
        return trainSessionService.getAllEnrollmentUserByName(trainSessionName);
    }
    @GetMapping("/all/get/enrolled-users/id/{trainSessionId}")
    public ResponseEntity<List<UserEntityDto>> getEnrolledUsersForTrainingSessionById(@PathVariable long trainSessionId)
    {
        return trainSessionService.getAllEnrollmentUserById(trainSessionId);
    }
    @GetMapping("/all/get/enrolled-pending-users/id/{trainSessionId}")
    public ResponseEntity<List<UserEntityDto>> getEnrolledUsersPendingForTrainingSession(@PathVariable long trainSessionId)
    {
        return trainSessionService.getEnrollmentUserRequests(trainSessionId);
    }
    @GetMapping("/all/get/enrolled-accepted-users/id/{trainSessionId}")
    public ResponseEntity<List<UserEntityDto>> getEnrolledUsersAcceptedForTrainingSession(@PathVariable long trainSessionId)
    {
        return trainSessionService.getEnrollmentUsersAccepted(trainSessionId);
    }
    @GetMapping("/all/get/enrolled-rejected-users/id/{trainSessionId}")
    public ResponseEntity<List<UserEntityDto>> getEnrolledUsersRejectedForTrainingSession(@PathVariable long trainSessionId)
    {
        return trainSessionService.getEnrollmentUserRejected(trainSessionId);
    }
    @GetMapping("/all/get/training-sessions")
    public ResponseEntity<List<TrainingSessionDto>> getAllTrainingSessions()
    {
        return trainSessionService.getAllTrainingSessions();
    }


    @GetMapping("/admin/update/is-user-enrolled")
    public ResponseEntity<Boolean> isUserEnrolled(@RequestBody @Valid EnrollmentRequestDTO enrollmentRequestDTO)
    {
        return trainSessionService.isUserEnrolled(enrollmentRequestDTO);
    }



    @PutMapping("/admin/update/id/{trainSessionId}")
    public ResponseEntity<String> modifyTrainingSessionById(@PathVariable long trainSessionId,@Valid @RequestBody TrainingSessionDto trainingSessionDto)
    {
        return trainSessionService.modifyTrainingSessionById(trainSessionId,trainingSessionDto);
    }
    @PutMapping("/admin/update/remove-user")
    public ResponseEntity<String> removeEnrollmentForUserById(@RequestBody @Valid EnrollmentRequestDTO enrollmentRequestDTO)
    {
        return trainSessionService.removeEnrollmentForUserById(enrollmentRequestDTO);
    }
    @PutMapping("/admin/update/accept-user")
    public ResponseEntity<String> acceptUser(@RequestBody @Valid EnrollmentRequestDTO enrollmentRequestDTO)
    {
        return trainSessionService.acceptUser(enrollmentRequestDTO);
    }
    @PutMapping("/admin/update/reject-user")
    public ResponseEntity<String> rejectUser(@RequestBody @Valid EnrollmentRequestDTO enrollmentRequestDTO)
    {
        return trainSessionService.rejectUser(enrollmentRequestDTO);
    }
    @PutMapping("/all/update/add-user")
    public ResponseEntity<String> enrollUserInTrainingSessionById(@RequestBody @Valid EnrollmentRequestDTO enrollmentRequestDTO)
    {

        return trainSessionService.enrollUserInTrainingSessionById(enrollmentRequestDTO);
    }



}
