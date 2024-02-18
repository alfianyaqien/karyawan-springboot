package com.alfian.test.controller;

import com.alfian.test.dto.KaryawanDto;
import com.alfian.test.model.Karyawan;
import com.alfian.test.service.KaryawanService;
import com.alfian.test.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        KaryawanDto savedKaryawanDto = karyawanService.saveKaryawan(karyawan);
        ApiResponse response = new ApiResponse(200, "success", savedKaryawanDto);
        return ResponseEntity.ok(response);
    }

}
