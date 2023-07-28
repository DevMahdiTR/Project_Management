package com.example.pfe_project.controllers.feed_back;



import com.example.pfe_project.dto.feed_back.FeedBackUserDto;
import com.example.pfe_project.dto.feed_back.FeedBackUserRequestDto;
import com.example.pfe_project.service.feed_back.FeedBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/feed_back_user")
@RestController
public class FeedBackUserController {


    private final FeedBackUserService feedBackUserService;

    @Autowired
    public FeedBackUserController(FeedBackUserService feedBackUserService) {
        this.feedBackUserService = feedBackUserService;
    }


    @PostMapping("/all/add_comment")
    public ResponseEntity<String> addCommentToFormById(@Valid @RequestBody FeedBackUserRequestDto feedBackUserRequestDto) {
        return this.feedBackUserService.addCommentToFormById(feedBackUserRequestDto);
    }


    @GetMapping("/all/get/form/{feedBackId}")
    public ResponseEntity<List<FeedBackUserDto>> getAllFeedBackOfFormById(@PathVariable("feedBackId") final long feedBackId) {
        return this.feedBackUserService.getAllFeedBackOfFormById(feedBackId);
    }


    @GetMapping("/all/get/form/{feedBackId}/user/{userId}")
    public ResponseEntity<List<FeedBackUserDto>> getAllFeedBackOfUserInForm(@PathVariable("feedBackId") long feedBackId, @PathVariable("userId") long userId) {
        return this.feedBackUserService.getAllFeedBackOfUserInForm(feedBackId,userId);
    }

    @DeleteMapping("/admin/delete/comment/{feedBackUserId}")
    public ResponseEntity<String> removeComment(@PathVariable("feedBackUserId") long feedBackUserId)
    {
        return this.feedBackUserService.deleteComment(feedBackUserId);
    }



}
