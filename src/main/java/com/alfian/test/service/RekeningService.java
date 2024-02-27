package com.alfian.test.service;

import com.alfian.test.model.Rekening;

public interface RekeningService {
    Rekening saveRekening(Rekening rekening, Long id_karyawan);
    Rekening updateRekening(Rekening rekening, Long id_karyawan);

}
