package com.example.pfe_project.controllers.feed_back;


import com.example.pfe_project.dto.feed_back.FeedBackDto;
import com.example.pfe_project.models.feedBack.FeedBack;
import com.example.pfe_project.service.feed_back.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/feed_back")
@RestController
public class FeedBackController {


    private final FeedBackService feedBackService;


    @Autowired
    public FeedBackController(FeedBackService feedBackService)
    {
        this.feedBackService = feedBackService;
    }



    @PostMapping("/admin/create")
    public ResponseEntity<String> createFeedBackForm(@Valid @RequestBody FeedBack feedBack)
    {
        return this.feedBackService.createFeedBackForm(feedBack);
    }

    @GetMapping("/all/get/all")
    public ResponseEntity<List<FeedBackDto>> getAllFeedBackForms()
    {
        return feedBackService.getAllFeedBackForms();
    }

    @PutMapping("/admin/update/{feedBackId}")
    public  ResponseEntity<String> modifyFeedBackFormById(@PathVariable("feedBackId") long feedBackId, @Valid @RequestBody FeedBack newFeedBack)
    {
        return this.feedBackService.modifyFeedBackFormById(feedBackId,newFeedBack);
    }

    @DeleteMapping("/admin/delete/{feedBackId}")
    public ResponseEntity<String> deleteFeedBackById(@PathVariable("feedBackId") final long feedBackId)
    {
        return this.feedBackService.deleteFeedBackById(feedBackId);
    }
}
