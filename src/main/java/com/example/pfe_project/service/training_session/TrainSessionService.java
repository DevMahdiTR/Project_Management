package com.example.pfe_project.service.training_session;


import com.example.pfe_project.dto.training_session.EnrollmentRequestDTO;
import com.example.pfe_project.dto.training_session.TrainingSessionDto;
import com.example.pfe_project.dto.user.UserEntityDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.training_session.EnrollmentStatus;
import com.example.pfe_project.models.training_session.TrainingSession;
import com.example.pfe_project.models.training_session.TrainingSessionUser;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.training_session.TrainSessionRepository;
import com.example.pfe_project.repository.training_session.TrainingSessionUserRepository;
import com.example.pfe_project.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TrainSessionService {
    private final TrainSessionRepository trainSessionRepository;
    private final UserRepository userRepository;
    private  final TrainingSessionUserRepository trainingSessionUserRepository;
    @Autowired
    public TrainSessionService(TrainSessionRepository trainSessionRepository, TrainingSessionUserRepository trainingSessionUserRepository, UserRepository userRepository)
    {
        this.trainSessionRepository = trainSessionRepository;
        this.trainingSessionUserRepository = trainingSessionUserRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createTrainingSessions(TrainingSession trainingSession)
    {

        trainSessionRepository.save(trainingSession);
        final String responseMessage = String.format("Training Session %s created successfully.",trainingSession.getName());
        return new ResponseEntity<String>(responseMessage, HttpStatus.CREATED);
    }

    public ResponseEntity<TrainingSessionDto> fetchTrainingSessionWithId(long trainSessionId)
    {
        TrainingSession trainingSessionSaved = getTrainingSessionById(trainSessionId);
        TrainingSessionDto trainingSessionDto = new TrainingSessionDto(trainingSessionSaved);
        return  ResponseEntity.ok(trainingSessionDto);
    }

    public ResponseEntity<TrainingSessionDto> fetchTrainingSessionWithName(String trainSessionName)
    {
        TrainingSession trainingSessionSaved = getTrainingSessionByName(trainSessionName);
        TrainingSessionDto trainingSessionDto = new TrainingSessionDto(trainingSessionSaved);
        return  ResponseEntity.ok(trainingSessionDto);
    }


    public ResponseEntity<String> modifyTrainingSessionById(long trainSessionId,TrainingSessionDto trainingSessionDto)
    {
        TrainingSession trainingSessionSaved = getTrainingSessionById(trainSessionId);
        trainingSessionSaved.setName(trainingSessionDto.getName());
        trainingSessionSaved.setDescription(trainingSessionDto.getDescription());

        trainSessionRepository.save(trainingSessionSaved);

        final String responseMessage = String.format("The training session with ID %d has been successfully modified.", trainSessionId);
        return ResponseEntity.ok(responseMessage);
    }


    @Transactional
    public ResponseEntity<String> deleteTrainingSessionById(long trainSessionId)
    {
        TrainingSession trainingSessionSaved = getTrainingSessionById(trainSessionId);
        trainingSessionUserRepository.removeEnrollmentForUserRelatedToTrainingSessionById(trainSessionId);
        trainSessionRepository.deleteTrainingSessionById(trainSessionId);
        final String responseMessage = String.format("The training sessions with ID %d has been successfully deleted.", trainSessionId);
        return ResponseEntity.ok(responseMessage);
    }

    @Transactional
    public ResponseEntity<String> deleteTrainingSessionByName(String trainSessionName)
    {
        TrainingSession trainingSessionSaved = getTrainingSessionByName(trainSessionName);
        trainingSessionUserRepository.removeEnrollmentForUserRelatedToTrainingSessionById(trainingSessionSaved.getId());
        trainSessionRepository.deleteTrainingSessionByName(trainSessionName);
        final String responseMessage = String.format("The training sessions with NAME  %s has been successfully deleted.", trainSessionName);
        return ResponseEntity.ok(responseMessage);
    }



    public ResponseEntity<List<UserEntityDto>> getEnrollmentUserRequests(long trainSessionId)
    {
        return getEnrollment(trainSessionId,trainingSessionUserRepository::getEnrollmentRequests);
    }
    public ResponseEntity<List<UserEntityDto>> getEnrollmentUsersAccepted(long trainSessionId)
    {
        return getEnrollment(trainSessionId,trainingSessionUserRepository::getEnrollmentUserAccepted);
    }
    public ResponseEntity<List<UserEntityDto>> getEnrollmentUserRejected(long trainSessionId)
    {
        return getEnrollment(trainSessionId,trainingSessionUserRepository::getEnrollmentUserRejected);
    }
    public ResponseEntity<List<UserEntityDto>> getAllEnrollmentUserById(long trainSessionId)
    {
        return getEnrollment(trainSessionId,trainingSessionUserRepository::getAllEnrollmentUserById);
    }
    public ResponseEntity<List<UserEntityDto>> getAllEnrollmentUserByName(String trainSessionName)
    {
        List<UserEntity> usersSaved = trainingSessionUserRepository.getAllEnrollmentUserByName(trainSessionName);
        List<UserEntityDto> usersDto = usersSaved.stream()
                .map(UserEntityDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usersDto);
    }

    public ResponseEntity<List<TrainingSessionDto>> getAllTrainingSessions()
    {
        List<TrainingSessionDto> trainingSessionDtoList = trainSessionRepository.fetchAllTrainingSessions().stream()
                .map(TrainingSessionDto::new)
                .toList();
        return ResponseEntity.ok(trainingSessionDtoList);
    }

    @Transactional
    public ResponseEntity<String> removeEnrollmentForUserById(@NotNull EnrollmentRequestDTO enrollmentRequestDTO)
    {
        TrainingSession trainingSession = getTrainingSessionById(enrollmentRequestDTO.getTrainSessionId());
        UserEntity user = getUserById(enrollmentRequestDTO.getUserId());
        TrainingSessionUser trainingSessionUser = getTrainingSessionUser(enrollmentRequestDTO);
        trainingSessionUserRepository.removeEnrollmentForUserById(enrollmentRequestDTO.getTrainSessionId(),enrollmentRequestDTO.getUserId());

        final String responseMessage = String
                .format("The user with ID %d has been successfully removed from the training session named %s.",
                        enrollmentRequestDTO.getUserId(),
                        trainingSession.getName())
                ;
        return ResponseEntity.ok(responseMessage);
    }

    @Transactional
    public ResponseEntity<String> enrollUserInTrainingSessionById(@NotNull EnrollmentRequestDTO enrollmentRequestDTO)
    {
        TrainingSession trainingSession = getTrainingSessionById(enrollmentRequestDTO.getTrainSessionId());
        UserEntity user = getUserById(enrollmentRequestDTO.getUserId());

        if(trainingSessionUserRepository.isUserEnrolledInTrainingSession(enrollmentRequestDTO.getTrainSessionId(),enrollmentRequestDTO.getUserId()))
        {
            final String responseMessage = String
                    .format("The user with ID %d is already enrolled in the training session named %s.",
                            enrollmentRequestDTO.getTrainSessionId(),
                            trainingSession.getName())
                    ;

            return ResponseEntity.ok(responseMessage);
        }

        TrainingSessionUser trainingSessionUser = new TrainingSessionUser();
        trainingSessionUser.setTrainingSession(trainingSession);
        trainingSessionUser.setUserEntity(user);
        trainingSessionUser.setEnrollmentStatus(EnrollmentStatus.PENDING);
        trainingSession.getEnrollmentRequests().add(trainingSessionUser);
        trainSessionRepository.save(trainingSession);

        final String responseMessage = String
                .format("The user with ID %d has been successfully added to the training session named %s.",
                        enrollmentRequestDTO.getTrainSessionId(),
                        trainingSession.getName())
                ;
        return ResponseEntity.ok(responseMessage);
    }

    public ResponseEntity<String> acceptUser(@NotNull EnrollmentRequestDTO enrollmentRequestDTO) {
        TrainingSession trainingSession = getTrainingSessionById(enrollmentRequestDTO.getTrainSessionId());
        UserEntity user = getUserById(enrollmentRequestDTO.getUserId());
        TrainingSessionUser trainingSessionUser = getTrainingSessionUser(enrollmentRequestDTO);



        TrainingSessionUser trainingSessionUserToAccept = trainingSession.getEnrollmentRequests().stream().filter(tsu -> tsu.equals(trainingSessionUser)).findFirst().get();
        trainingSessionUserToAccept.setEnrollmentStatus(EnrollmentStatus.ACCEPTED);
        trainSessionRepository.save(trainingSession);
        final String responseMessage = String
                .format("The user with ID %d has been successfully accepted into the training session named %s.",
                        enrollmentRequestDTO.getTrainSessionId(),
                        trainingSession.getName())
                ;

        return ResponseEntity.ok(responseMessage);
    }
    public ResponseEntity<String> rejectUser(@NotNull EnrollmentRequestDTO enrollmentRequestDTO) {
        TrainingSession trainingSession = getTrainingSessionById(enrollmentRequestDTO.getTrainSessionId());
        UserEntity user = getUserById(enrollmentRequestDTO.getUserId());
        TrainingSessionUser trainingSessionUser = getTrainingSessionUser(enrollmentRequestDTO);

        TrainingSessionUser trainingSessionUserToAccept = trainingSession.getEnrollmentRequests().stream().filter(tsu -> tsu.equals(trainingSessionUser)).findFirst().get();
        trainingSessionUserToAccept.setEnrollmentStatus(EnrollmentStatus.REJECTED);
        trainSessionRepository.save(trainingSession);
        final String responseMessage = String
                .format("The user with ID %d has been successfully rejected from the training session named %s.",
                        enrollmentRequestDTO.getTrainSessionId(),
                        trainingSession.getName())
                ;

        return ResponseEntity.ok(responseMessage);
    }

   public ResponseEntity<Boolean> isUserEnrolled(@NotNull EnrollmentRequestDTO enrollmentRequestDTO) {
        final boolean isEnrolled = trainingSessionUserRepository.isUserEnrolledInTrainingSession(enrollmentRequestDTO.getTrainSessionId(),enrollmentRequestDTO.getUserId());
        return ResponseEntity.ok(isEnrolled);
    }



    public TrainingSession getTrainingSessionById(long trainSessionId) {
        return trainSessionRepository.fetchTrainingSessionById(trainSessionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The training session with ID %d could not be found.", trainSessionId)));
    }
    public TrainingSessionUser getTrainingSessionUser(@NotNull EnrollmentRequestDTO enrollmentRequestDTO) {
        return trainingSessionUserRepository.fetchTrainingSessionUserUserIdTrainingSessionId(enrollmentRequestDTO.getTrainSessionId(),enrollmentRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d is not enrolled to the training session with ID %d",enrollmentRequestDTO.getUserId(),enrollmentRequestDTO.getTrainSessionId())));
    }

    public UserEntity getUserById(long userId) {
        return userRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d could not be found.", userId)));
    }
    public TrainingSession getTrainingSessionByName(String trainSessionName) {
        return trainSessionRepository.fetchTrainingSessionByName(trainSessionName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The training session with name '%s' could not be found.", trainSessionName)));
    }


    private @NotNull ResponseEntity<List<UserEntityDto>> getEnrollment(long trainSessionId, @NotNull Function<Long, List<UserEntity>> fetchFunction) {
        List<UserEntity> usersSaved = fetchFunction.apply(trainSessionId);
        List<UserEntityDto> usersDto = usersSaved.stream()
                .map(UserEntityDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usersDto);
    }


}
