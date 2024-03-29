package com.alfian.test.service;

import com.alfian.test.model.Karyawan;
import org.springframework.data.domain.Page;

public interface KaryawanService {
    Karyawan saveKaryawan(Karyawan karyawan);
    Karyawan getKaryawanById(Long id);
    Karyawan deleteKaryawan(Karyawan karyawan);
    Karyawan updateKaryawan(Karyawan karyawan);
    Page<Karyawan> getAllKaryawan(int size, int page);
}
