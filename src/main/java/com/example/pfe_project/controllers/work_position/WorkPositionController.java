package com.example.pfe_project.controllers.work_position;

import com.example.pfe_project.models.work_position.WorkPosition;
import com.example.pfe_project.service.work_position.WorkPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("api/v1/work_position")
@RestController
public class WorkPositionController {
    private final WorkPositionService workPositionService;

    @Autowired
    public WorkPositionController(WorkPositionService workPositionService) {
        this.workPositionService = workPositionService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewWorkPosition(@NotNull @RequestBody WorkPosition workPosition) {
        return workPositionService.createNewWorkPosition(workPosition);
    }

    @GetMapping("/get/{workPositionId}")
    public ResponseEntity<WorkPosition> fetchById(@PathVariable("workPositionId") long workPositionId) {
        return workPositionService.fetchById(workPositionId);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<WorkPosition>> fetchAllWorkPosition() {
        return workPositionService.fetchAllWorkPosition();
    }

    @PutMapping("/update/{workPositionId}")
    public ResponseEntity<String> modifyWorkPosition(
            @PathVariable("workPositionId") long workPositionId,
            @NotNull @RequestBody WorkPosition workPosition
    ) {
        return workPositionService.modifyWorkPosition(workPositionId, workPosition);
    }

    @DeleteMapping("/remove/{workPositionId}")
    public ResponseEntity<String> deleteWorkPositionById(@PathVariable("workPositionId") long workPositionId) {
        return workPositionService.deleteWorkPositionById(workPositionId);
    }

    @PutMapping("/add_work_position/{userId}/assign/{workPositionId}")
    public ResponseEntity<String> addWorkPositionToUser(
            @PathVariable("userId") long userId,
            @PathVariable("workPositionId") long workPositionId
    ) {
        return workPositionService.addWorkPositionToUser(userId, workPositionId);
    }

    @PutMapping("/remove_work_position/remove/{userId}")
    public ResponseEntity<String> removeUserWorkPosition(
            @PathVariable("userId") long userId
    ) {
        return workPositionService.removeUserWorkPosition(userId);
    }
}
