package com.alfian.test.controller;

import com.alfian.test.model.Rekening;
import com.alfian.test.service.RekeningService;
import com.alfian.test.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateRekening(@RequestBody Rekening rekening) {
        Long id_karyawan = Long.parseLong(String.valueOf(rekening.getKaryawan().getId()));
        Rekening rekeningUpdate = rekeningService.updateRekening(rekening, id_karyawan);

        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put("created_date", rekeningUpdate.getCreated_date());
        dataResponse.put("updated_date", rekeningUpdate.getUpdated_date());
        dataResponse.put("deleted_date", rekeningUpdate.getDeleted_date());
        dataResponse.put("id", rekeningUpdate.getId());
        dataResponse.put("nama", rekeningUpdate.getNama());
        dataResponse.put("jenis", rekeningUpdate.getJenis());
        dataResponse.put("rekening", rekeningUpdate.getRekening());
        dataResponse.put("alamat", rekeningUpdate.getAlamat());

        Map<String, Object> karyawanMap = new HashMap<>();
        karyawanMap.put("id", rekeningUpdate.getKaryawan().getId());
        karyawanMap.put("nama", rekeningUpdate.getKaryawan().getNama());
        dataResponse.put("karyawan", karyawanMap);
        ApiResponse response = new ApiResponse(200, "success", dataResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRekeningById(@PathVariable(value = "id") String id) {
        try {
            Rekening rekening = rekeningService.getRekeningById(Long.parseLong(id));

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("created_date", rekening.getCreated_date());
            dataResponse.put("updated_date", rekening.getUpdated_date());
            dataResponse.put("deleted_date", rekening.getDeleted_date());
            dataResponse.put("id", rekening.getId());
            dataResponse.put("nama", rekening.getNama());
            dataResponse.put("jenis", rekening.getJenis());
            dataResponse.put("rekening", rekening.getRekening());
            dataResponse.put("alamat", rekening.getAlamat());

            Map<String, Object> karyawanMap = new HashMap<>();
            karyawanMap.put("id", rekening.getKaryawan().getId());
            karyawanMap.put("nama", rekening.getKaryawan().getNama());
            dataResponse.put("karyawan", karyawanMap);
            ApiResponse response = new ApiResponse(200, "success", dataResponse);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rekening with id " + id +" not found");
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteRekening(@RequestBody Rekening rekening) {
        rekeningService.deleteRekening(rekening);
        ApiResponse response = new ApiResponse(200, "success", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllRekening(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Rekening> rekeningPage = rekeningService.getAllRekening(size, page);

        // Create a list to hold the data of individual Rekening objects
        List<Map<String, Object>> rekeningDataList = new ArrayList<>();

        // Iterate over the contents of the page
        for (Rekening rekening : rekeningPage.getContent()) {
            Map<String, Object> rekeningData = new HashMap<>();
            rekeningData.put("created_date", rekening.getCreated_date());
            rekeningData.put("updated_date", rekening.getUpdated_date());
            rekeningData.put("deleted_date", rekening.getDeleted_date());
            rekeningData.put("id", rekening.getId());
            rekeningData.put("nama", rekening.getNama());
            rekeningData.put("jenis", rekening.getJenis());
            rekeningData.put("rekening", rekening.getRekening());
            rekeningData.put("alamat", rekening.getAlamat());

            // Assuming Karyawan is a nested object within Rekening
            Map<String, Object> karyawanMap = new HashMap<>();
            karyawanMap.put("id", rekening.getKaryawan().getId());
            karyawanMap.put("nama", rekening.getKaryawan().getNama());
            rekeningData.put("karyawan", karyawanMap);

            rekeningDataList.add(rekeningData);
        }

        // Prepare the response with pagination details
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", rekeningDataList);
        responseData.put("currentPage", rekeningPage.getNumber());
        responseData.put("totalItems", rekeningPage.getTotalElements());
        responseData.put("totalPages", rekeningPage.getTotalPages());

        ApiResponse response = new ApiResponse(200, "Data fetched successfully", responseData);
        return ResponseEntity.ok(response);
    }

}
