package com.example.pfe_project.service.work_position;


import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.models.work_position.WorkPosition;
import com.example.pfe_project.repository.user.UserRepository;
import com.example.pfe_project.repository.work_position.WorkPositionRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
@Service
public class WorkPositionService {



    private final WorkPositionRepository workPositionRepository;
    private final UserRepository userRepository;


    @Autowired
    public  WorkPositionService(WorkPositionRepository workPositionRepository, UserRepository userRepository)
    {
        this.workPositionRepository =workPositionRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<String> createNewWorkPosition(@NotNull WorkPosition workPosition)
    {
        workPositionRepository.save(workPosition);

        final String successResponse = String.format("Working position with name %s created successfully.", workPosition.getPositionName());
        return new ResponseEntity<String>(successResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<WorkPosition> fetchById(final long workPositionId)
    {
        WorkPosition workPosition = getWorkPositionById(workPositionId);
        return new ResponseEntity<>(workPosition,HttpStatus.OK);
    }

    public ResponseEntity<List<WorkPosition>> fetchAllWorkPosition()
    {
        List<WorkPosition> workPositionList = workPositionRepository.fetchAllWorkPosition();

        return new ResponseEntity<List<WorkPosition>>(workPositionList,HttpStatus.OK);
    }

    public ResponseEntity<String> modifyWorkPosition(final long workPositionId, @NotNull  WorkPosition workPosition)
    {
        WorkPosition workPositionSaved = getWorkPositionById(workPositionId);
        workPositionSaved.setPositionName(workPosition.getPositionName());
        workPositionSaved.setSalary(workPosition.getSalary());

        workPositionRepository.save(workPositionSaved);

        final String successResponse = String.format("Work Position with ID %d has modified successfully.",workPositionId);
        return new ResponseEntity<String>(successResponse,  HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<String> deleteWorkPositionById(final long workPositionId)
    {

        WorkPosition workPosition = getWorkPositionById(workPositionId);
        workPositionRepository.deleteWorkPositionById(workPositionId);

        final String successResponse = String.format("Work Position with ID %s is delete successfully.",workPositionId);
        return new ResponseEntity<String>(successResponse, HttpStatus.OK);
    }



    public ResponseEntity<String> addWorkPositionToUser(final long userId, final long workPositionId)
    {
        UserEntity userEntity = getUserById(userId);
        WorkPosition workPosition = getWorkPositionById(workPositionId);
        userEntity.setWorkPosition(workPosition);
        userRepository.save(userEntity);

        final String successResponse = String.format("User with ID %d modified to have Work position with name %s.",userId,workPosition.getPositionName());
        return new ResponseEntity<String>(successResponse, HttpStatus.OK);
    }
    public ResponseEntity<String> removeUserWorkPosition(final long userId)
    {
        UserEntity userEntity = getUserById(userId);

        WorkPosition userWorkPosition = userEntity.getWorkPosition();


        if(userWorkPosition == null)
        {
            final String failedResponse = String.format("User with ID %d already does not have a work position.",userId);
            return new ResponseEntity<String>(failedResponse,HttpStatus.BAD_REQUEST);
        }
        userEntity.setWorkPosition(null);
        userRepository.save(userEntity);


        final String successResponse = String.format("User with ID %d has no longer a working position.",userId);
        return new ResponseEntity<String>(successResponse, HttpStatus.OK);
    }

    private WorkPosition getWorkPositionById(final long workPositionId)
    {
        return workPositionRepository.fetchWorkPositionById(workPositionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The role with name %s could not be found.", workPositionId)));
    }

    public UserEntity getUserById(long userId) {
        return userRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d could not be found.", userId)));
    }

}
