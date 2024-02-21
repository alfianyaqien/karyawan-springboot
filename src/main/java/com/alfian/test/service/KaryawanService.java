package com.alfian.test.service;

import com.alfian.test.model.Karyawan;

public interface KaryawanService {
    Karyawan saveKaryawan(Karyawan karyawan);
    Karyawan getKaryawanById(Long id);
}
