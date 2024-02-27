package com.alfian.test.service;

import com.alfian.test.model.Rekening;
import org.springframework.data.domain.Page;

public interface RekeningService {
    Rekening saveRekening(Rekening rekening, Long id_karyawan);
    Rekening updateRekening(Rekening rekening, Long id_karyawan);
    Rekening getRekeningById(Long id);
    Rekening deleteRekening(Rekening rekening);
    Page<Rekening> getAllRekening(int size, int page);

}
