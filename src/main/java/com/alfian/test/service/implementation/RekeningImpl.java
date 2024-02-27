package com.alfian.test.service.implementation;

import com.alfian.test.model.Karyawan;
import com.alfian.test.model.Rekening;
import com.alfian.test.repository.KaryawanRepository;
import com.alfian.test.repository.RekeningRepository;
import com.alfian.test.service.RekeningService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    @Override
    public Rekening updateRekening(Rekening rekening, Long id_karyawan) {
        Karyawan checkId = karyawanRepository.findById(id_karyawan).orElseThrow(null);
        Rekening updateRekening = rekeningRepository.getById(rekening.getId());
        updateRekening.setNama(rekening.getNama());
        updateRekening.setJenis(rekening.getJenis());
        updateRekening.setRekening(rekening.getRekening());
        updateRekening.setAlamat(rekening.getAlamat());
        updateRekening.setKaryawan(checkId);

        Date date = new Date(System.currentTimeMillis());
        updateRekening.setUpdated_date(date);

        return rekeningRepository.save(updateRekening);
    }

    @Override
    public Rekening getRekeningById(Long id) {
        Optional<Rekening> rekening = rekeningRepository.findById(id);
        if (rekening.isPresent()) {
            return rekening.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Rekening deleteRekening(Rekening rekening) {
        Rekening checkIdRekening = rekeningRepository.getById(rekening.getId());
        checkIdRekening.setDeleted_date(new Date());
        return rekeningRepository.save(checkIdRekening);
    }

    @Override
    public Page<Rekening> getAllRekening(int size, int page) {
        Pageable showData = PageRequest.of(page, size);
        return rekeningRepository.findAll(showData);
    }

}
