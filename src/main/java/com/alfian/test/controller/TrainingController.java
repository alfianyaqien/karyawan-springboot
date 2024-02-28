package com.alfian.test.controller;

import com.alfian.test.model.Training;
import com.alfian.test.service.TrainingService;
import com.alfian.test.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/v1/idstar/training")
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveTraining(@RequestBody Training training) {
        Training trainingNew = trainingService.saveTraining(training);
        ApiResponse response = new ApiResponse(200, "success", trainingNew);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateTraining(@RequestBody Training training) {
        Training trainingUpdate = trainingService.updateTraining(training);
        ApiResponse response = new ApiResponse(200, "success", trainingUpdate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllTraining(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Training> trainingPage = trainingService.getAllTraining(size, page);
        ApiResponse response = new ApiResponse(200, "Data fetched successfully", trainingPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainingById(@PathVariable(value = "id") String id) {
        try {
            Training training = trainingService.getTrainingById(Long.parseLong(id));
            ApiResponse response = new ApiResponse(200, "success", training);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training with id " + id +" not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteTraining(@RequestBody Training training) {
        trainingService.deleteTraining(training);
        ApiResponse response = new ApiResponse(200, "success", "success");
        return ResponseEntity.ok(response);
    }
}
