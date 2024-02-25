package com.alfian.test.controller;

import com.alfian.test.model.Rekening;
import com.alfian.test.service.RekeningService;
import com.alfian.test.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/v1/idstar/rekening")
public class RekeningController {
    private final RekeningService rekeningService;

    @Autowired
    public RekeningController(RekeningService rekeningService) {
        this.rekeningService = rekeningService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveRekening(@RequestBody Rekening rekening) {
        // Assuming the Karyawan object inside Rekening has the ID set
        if (rekening.getKaryawan() == null || rekening.getKaryawan().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID Karyawan is required");
        }

        Long id_karyawan = Long.parseLong(String.valueOf(rekening.getKaryawan().getId()));
        Rekening rekeningNew = rekeningService.saveRekening(rekening, id_karyawan);
        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put("created_date", rekeningNew.getCreated_date());
        dataResponse.put("updated_date", rekeningNew.getUpdated_date());
        dataResponse.put("deleted_date", rekeningNew.getDeleted_date());
        dataResponse.put("id", rekeningNew.getId());
        dataResponse.put("nama", rekeningNew.getNama());
        dataResponse.put("jenis", rekeningNew.getJenis());
        dataResponse.put("rekening", rekeningNew.getRekening());
        dataResponse.put("alamat", rekeningNew.getAlamat());

        Map<String, Object> karyawanMap = new HashMap<>();
        karyawanMap.put("id", rekeningNew.getKaryawan().getId());
        karyawanMap.put("nama", rekeningNew.getKaryawan().getNama());
        dataResponse.put("karyawan", karyawanMap);

        ApiResponse response = new ApiResponse(200, "success", dataResponse);
        return ResponseEntity.ok(response);
    }

}
