package com.alfian.test.service.implementation;

import com.alfian.test.model.Karyawan;
import com.alfian.test.model.Rekening;
import com.alfian.test.repository.KaryawanRepository;
import com.alfian.test.repository.RekeningRepository;
import com.alfian.test.service.RekeningService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RekeningImpl implements RekeningService {
    private final RekeningRepository rekeningRepository;
    private final KaryawanRepository karyawanRepository;

    @Autowired
    public RekeningImpl(RekeningRepository rekeningRepository, KaryawanRepository karyawanRepository) {
        this.rekeningRepository = rekeningRepository;
        this.karyawanRepository = karyawanRepository;
    }

    @Override
    public Rekening saveRekening(Rekening rekening, Long id_karyawan) {
        Karyawan checkId = karyawanRepository.findById(id_karyawan).orElseThrow(null);
        rekening.setKaryawan(checkId);
        return rekeningRepository.save(rekening);
    }

}
