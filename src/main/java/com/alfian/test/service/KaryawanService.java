package com.alfian.test.service;

import com.alfian.test.dto.KaryawanDto;
import com.alfian.test.model.Karyawan;

public interface KaryawanService {
    KaryawanDto saveKaryawan(Karyawan karyawan);
}
