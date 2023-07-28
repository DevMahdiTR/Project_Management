package com.example.pfe_project.service.feed_back;


import com.example.pfe_project.dto.feed_back.FeedBackUserDto;
import com.example.pfe_project.dto.feed_back.FeedBackUserRequestDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.feedBack.FeedBack;
import com.example.pfe_project.models.feedBack.FeedBackUser;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.feed_back.FeedBackRepository;
import com.example.pfe_project.repository.feed_back.FeedBackUserRepository;
import com.example.pfe_project.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackUserService {

    private final FeedBackRepository feedBackRepository;
    private final UserRepository userRepository;
    private final FeedBackUserRepository feedBackUserRepository;


    @Autowired
    public FeedBackUserService(FeedBackRepository feedBackRepository, UserRepository userRepository, FeedBackUserRepository feedBackUserRepository) {
        this.feedBackRepository = feedBackRepository;
        this.userRepository = userRepository;
        this.feedBackUserRepository = feedBackUserRepository;
    }


    public ResponseEntity<String> addCommentToFormById(@NotNull final FeedBackUserRequestDto feedBackUserRequestDto) {
        FeedBack feedBack = getFeedBacKById(feedBackUserRequestDto.getFeedBackId());
        UserEntity userEntity = getUserById(feedBackUserRequestDto.getUserId());

        FeedBackUser feedBackUser = new FeedBackUser();
        feedBackUser.setFeedBack(feedBack);
        feedBackUser.setUserEntity(userEntity);
        feedBackUser.setComment(feedBackUserRequestDto.getComment());

        feedBack.getFeedBackUsers().add(feedBackUser);
        feedBackRepository.save(feedBack);

        final String successResponse = String.format("Feed Back Comment is saved successfully in form with NAME %S.", feedBack.getFeedBackName());
        return new ResponseEntity<String>(successResponse, HttpStatus.CREATED);
    }


    public ResponseEntity<List<FeedBackUserDto>> getAllFeedBackOfFormById(final long feedBackId) {
        FeedBack feedBack = getFeedBacKById(feedBackId);
        List<FeedBackUserDto> feedBackUserRequestDtoList = feedBackUserRepository.fetchAllCommentInFormById(feedBackId)
                .stream()
                .map(FeedBackUserDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<List<FeedBackUserDto>>(feedBackUserRequestDtoList, HttpStatus.OK);

    }

    public ResponseEntity<List<FeedBackUserDto>> getAllFeedBackOfUserInForm(final long feedBackId , final long userId) {
        FeedBack feedBack = getFeedBacKById(feedBackId);
        UserEntity userEntity = getUserById(userId);
        List<FeedBackUserDto> feedBackUserRequestDtoList = feedBackUserRepository
                .fetchAllCommentOfUserInForm(feedBackId,userId)
                .stream()
                .map(FeedBackUserDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<List<FeedBackUserDto>>(feedBackUserRequestDtoList, HttpStatus.OK);

    }
    @Transactional
    public ResponseEntity<String> deleteComment(final long feedBackUserId) {

        FeedBackUser feedBackUser = getFeedBacKUserById(feedBackUserId);
        feedBackUserRepository.deleteCommentById(feedBackUserId);

        final String successResponse = String.format("Comment with ID %d is deleted successfully.",feedBackUserId);
        return new ResponseEntity<String>(successResponse,HttpStatus.OK);
    }



    private FeedBack getFeedBacKById(final long feedBackId) {
        return feedBackRepository.fetchFeedBackById(feedBackId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Feed Back Form with ID %s does not exist.", feedBackId)));
    }

    public UserEntity getUserById(long userId) {
        return userRepository.fetchUserWithId(userId)

                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d could not be found.", userId)));
    }

    private FeedBackUser getFeedBacKUserById(final long feedBackUserId) {
        return feedBackUserRepository.fetchFeedBackUserById(feedBackUserId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The comment with ID %s does not exist.", feedBackUserId)));
    }

}