package com.alfian.test.service.implementation;

import com.alfian.test.model.DetailKaryawan;
import com.alfian.test.model.Karyawan;
import com.alfian.test.repository.DetailKaryawanRepository;
import com.alfian.test.repository.KaryawanRepository;
import com.alfian.test.service.KaryawanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class KaryawanImpl implements KaryawanService {

    private final KaryawanRepository karyawanRepository;
    private final DetailKaryawanRepository detailKaryawanRepository;
    @Autowired
    public KaryawanImpl(KaryawanRepository karyawanRepository, DetailKaryawanRepository detailKaryawanRepository) {
        this.karyawanRepository = karyawanRepository;
        this.detailKaryawanRepository = detailKaryawanRepository;
    }

    @Override
    @Transactional
    public Karyawan saveKaryawan(Karyawan karyawan) {

        // Save the Karyawan entity.
        Karyawan karyawanNew = karyawanRepository.save(karyawan);

        // Check if DetailKaryawan already exists for this Karyawan
        DetailKaryawan existingDetail = detailKaryawanRepository.findById(karyawanNew.getId()).orElse(null);

        if (existingDetail != null) {
            // Update the existing DetailKaryawan entity.
            existingDetail.setNik(karyawan.getDetailKaryawan().getNik());
            existingDetail.setNpwp(karyawan.getDetailKaryawan().getNpwp());
        } else {
            // Create and save the new DetailKaryawan entity.
            DetailKaryawan dataDetailKry = new DetailKaryawan();
            dataDetailKry.setNik(karyawan.getDetailKaryawan().getNik());
            dataDetailKry.setNpwp(karyawan.getDetailKaryawan().getNpwp());
            dataDetailKry.setKaryawan(karyawanNew);
            existingDetail = detailKaryawanRepository.save(dataDetailKry);
        }

        // Set the DetailKaryawan to Karyawan to complete the bidirectional link.
        karyawanNew.setDetailKaryawan(existingDetail);
        // Save the Karyawan entity again to update it with the associated DetailKaryawan.
        return karyawanRepository.save(karyawanNew);
    }

    @Override
    public Karyawan getKaryawanById(Long id) {
        Optional<Karyawan> karyawan = karyawanRepository.findById(id);
        if (karyawan.isPresent()) {
            return karyawan.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Karyawan deleteKaryawan(Karyawan karyawan) {
        Karyawan checkIdKaryawan = karyawanRepository.getById(karyawan.getId());
        checkIdKaryawan.setDeleted_date(new Date());
        return karyawanRepository.save(checkIdKaryawan);
    }

    @Override
    public Karyawan updateKaryawan(Karyawan karyawan) {
        Karyawan updateKaryawan = karyawanRepository.getById(karyawan.getId());

        updateKaryawan.setNama(karyawan.getNama());
        updateKaryawan.setDob(karyawan.getDob());
        updateKaryawan.setAlamat(karyawan.getAlamat());
        updateKaryawan.setStatus(karyawan.getStatus());
        updateKaryawan.getDetailKaryawan().setNik(karyawan.getDetailKaryawan().getNik());
        updateKaryawan.getDetailKaryawan().setNpwp(karyawan.getDetailKaryawan().getNpwp());

        return karyawanRepository.save(updateKaryawan);
    }

    @Override
    public Page<Karyawan> getAllKaryawan(int size, int page) {
        Pageable showData = PageRequest.of(page, size);
        return karyawanRepository.findAll(showData);
    }

}
