package com.alfian.test.controller;

import com.alfian.test.model.KaryawanTraining;
import com.alfian.test.model.Training;
import com.alfian.test.service.KaryawanTrainingService;
import com.alfian.test.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/v1/idstar/karyawan-training")
public class KaryawanTrainingController {
    private final KaryawanTrainingService karyawanTrainingService;

    @Autowired
    public KaryawanTrainingController(KaryawanTrainingService karyawanTrainingService) {
        this.karyawanTrainingService = karyawanTrainingService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveKaryawanTraining(@RequestBody KaryawanTraining karyawanTraining) {
        KaryawanTraining newKaryawanTraining = karyawanTrainingService.saveKaryawanTraining(karyawanTraining);
        ApiResponse response = new ApiResponse(200, "success", newKaryawanTraining);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateKaryawanTraining(@RequestBody KaryawanTraining karyawanTraining) {
        KaryawanTraining updateKaryawanTraining = karyawanTrainingService.updateKaryawanTraining(karyawanTraining);
        ApiResponse response = new ApiResponse(200, "success", updateKaryawanTraining);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllKaryawanTraining(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<KaryawanTraining> karyawanTrainingPage = karyawanTrainingService.getAllKaryawanTraining(size, page);
        ApiResponse response = new ApiResponse(200, "Data fetched successfully", karyawanTrainingPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKaryawanTrainingById(@PathVariable(value = "id") String id) {
        try {
            KaryawanTraining karyawanTraining = karyawanTrainingService.getKaryawanTrainingById(Long.parseLong(id));
            ApiResponse response = new ApiResponse(200, "success", karyawanTraining);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan Training with id " + id +" not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteKaryawanTraining(@RequestBody KaryawanTraining karyawanTraining) {
        karyawanTrainingService.deleteKaryawanTraining(karyawanTraining);
        ApiResponse response = new ApiResponse(200, "success", "success");
        return ResponseEntity.ok(response);
    }
}
