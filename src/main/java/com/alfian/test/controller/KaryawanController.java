package com.alfian.test.controller;

import com.alfian.test.model.Karyawan;
import com.alfian.test.service.KaryawanService;
import com.alfian.test.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> getKaryawanById(@PathVariable Long id) {
        Karyawan karyawan = karyawanService.getKaryawanById(id);
        ApiResponse response = new ApiResponse(200, "success", karyawan);
        return ResponseEntity.ok(response);
    }

}
