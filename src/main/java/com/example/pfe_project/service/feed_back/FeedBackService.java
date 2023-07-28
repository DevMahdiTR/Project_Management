package com.example.pfe_project.service.feed_back;

import com.example.pfe_project.dto.feed_back.FeedBackDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.feedBack.FeedBack;
import com.example.pfe_project.repository.feed_back.FeedBackRepository;
import com.example.pfe_project.repository.feed_back.FeedBackUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {


    private final FeedBackRepository feedBackRepository;
    private final FeedBackUserRepository feedBackUserRepository;


    @Autowired
    public FeedBackService(FeedBackRepository feedBackRepository, FeedBackUserRepository feedBackUserRepository)
    {
        this.feedBackUserRepository = feedBackUserRepository;
        this.feedBackRepository = feedBackRepository;
    }


    public ResponseEntity<List<FeedBackDto>> getAllFeedBackForms()
    {
        List<FeedBackDto> feedBackDtoList = feedBackRepository.fetchAllFeedBackFroms().stream().map(FeedBackDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(feedBackDtoList,HttpStatus.OK);
    }
    public ResponseEntity<String> createFeedBackForm(final FeedBack feedBack)
    {
        feedBackRepository.save(feedBack);

        final String successResponse = "Feed Back is create successfully.";
        return new ResponseEntity<String>(successResponse, HttpStatus.CREATED);
    }


    public  ResponseEntity<String> modifyFeedBackFormById(final long feedBackId, @NotNull FeedBack newFeedBack)
    {
        FeedBack feedBack = getFeedBacKById(feedBackId);

        feedBack.setFeedBackName(newFeedBack.getFeedBackName());
        feedBackRepository.save(feedBack);

        final String successResponse = "Feed Back has modified successfully.";
        return new ResponseEntity<String>(successResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteFeedBackById(final long feedBackId)
    {
        FeedBack feedBack = getFeedBacKById(feedBackId);

        feedBackUserRepository.deleteAllCommentRelatedToForm(feedBackId);
        feedBackRepository.deleteFeedBackById(feedBackId);

        final String successResponse = String.format("Feed Back form with ID %d is deleted successfully.",feedBackId);
        return new ResponseEntity<String>(successResponse, HttpStatus.OK);
    }







    private FeedBack getFeedBacKById(final long feedBackId)
    {
        return feedBackRepository.fetchFeedBackById(feedBackId)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("Feed Back Form with ID %s does not exist.",feedBackId)));
    }
}
