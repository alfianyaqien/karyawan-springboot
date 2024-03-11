package com.alfian.test.service;

import com.alfian.test.model.KaryawanTraining;
import org.springframework.data.domain.Page;

public interface KaryawanTrainingService {
    KaryawanTraining saveKaryawanTraining(KaryawanTraining karyawanTraining);
    KaryawanTraining updateKaryawanTraining(KaryawanTraining karyawanTraining);
    Page<KaryawanTraining> getAllKaryawanTraining(int size, int page);
    KaryawanTraining getKaryawanTrainingById(Long id);
    KaryawanTraining deleteKaryawanTraining(KaryawanTraining karyawanTraining);
}
