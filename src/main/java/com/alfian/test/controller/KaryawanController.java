package com.alfian.test.controller;

import com.alfian.test.model.Karyawan;
import com.alfian.test.service.KaryawanService;
import com.alfian.test.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/v1/idstar/karyawan")
public class KaryawanController {
    private final KaryawanService karyawanService;

    @Autowired
    public KaryawanController(KaryawanService karyawanService) {
        this.karyawanService = karyawanService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveKaryawan(@RequestBody Karyawan karyawan) {
        Karyawan karyawanNew = karyawanService.saveKaryawan(karyawan);
        ApiResponse response = new ApiResponse(200, "success", karyawanNew);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKaryawanById(@PathVariable(value = "id") String id) {
        try {
            Karyawan karyawan = karyawanService.getKaryawanById(Long.parseLong(id));
            ApiResponse response = new ApiResponse(200, "success", karyawan);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id +" not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteKaryawan(@RequestBody Karyawan karyawan) {
        Karyawan karyawanDelete = karyawanService.deleteKaryawan(karyawan);
        ApiResponse response = new ApiResponse(200, "success", karyawanDelete);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateKaryawan(@RequestBody Karyawan karyawan) {
        Karyawan karyawanUpdate = karyawanService.updateKaryawan(karyawan);
        ApiResponse response = new ApiResponse(200, "success", karyawanUpdate);
        return ResponseEntity.ok(response);
    }

}
