package com.alfian.test.service.implementation;

import com.alfian.test.dto.DetailKaryawanDto;
import com.alfian.test.dto.KaryawanDto;
import com.alfian.test.model.DetailKaryawan;
import com.alfian.test.model.Karyawan;
import com.alfian.test.repository.DetailKaryawanRepository;
import com.alfian.test.repository.KaryawanRepository;
import com.alfian.test.service.KaryawanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KaryawanImpl implements KaryawanService {

    private final KaryawanRepository karyawanRepository;
    private final DetailKaryawanRepository detailKaryawanRepository;
    @Autowired
    public KaryawanImpl(KaryawanRepository karyawanRepository, DetailKaryawanRepository detailKaryawanRepository) {
        this.karyawanRepository = karyawanRepository;
        this.detailKaryawanRepository = detailKaryawanRepository;
    }

    private KaryawanDto mapToKaryawanDto(Karyawan karyawan) {
        KaryawanDto karyawanDto = new KaryawanDto();
        karyawanDto.setId(karyawan.getId());
        karyawanDto.setCreated_date(karyawan.getCreated_date());
        karyawanDto.setUpdated_date(karyawan.getUpdated_date());
        karyawanDto.setDeleted_date(karyawan.getDeleted_date());
        karyawanDto.setNama(karyawan.getNama());
        karyawanDto.setDob(karyawan.getDob());
        karyawanDto.setStatus(karyawan.getStatus());
        karyawanDto.setAlamat(karyawan.getAlamat());
        karyawanDto.setDetailKaryawan(mapToDetailKaryawanDto(karyawan.getDetailKaryawan()));

        return karyawanDto;
    }

    private DetailKaryawanDto mapToDetailKaryawanDto(DetailKaryawan detailKaryawan) {
        DetailKaryawanDto detailKaryawanDto = new DetailKaryawanDto();
        detailKaryawanDto.setId(detailKaryawan.getId());
        detailKaryawanDto.setCreated_date(detailKaryawan.getCreated_date());
        detailKaryawanDto.setUpdated_date(detailKaryawan.getUpdated_date());
        detailKaryawanDto.setDeleted_date(detailKaryawan.getDeleted_date());
        detailKaryawanDto.setNik(detailKaryawan.getNik());
        detailKaryawanDto.setNpwp(detailKaryawan.getNpwp());

        return detailKaryawanDto;
    }

    @Override
    @Transactional
    public KaryawanDto saveKaryawan(Karyawan karyawan) {
        karyawanRepository.save(karyawan);
        DetailKaryawan dataDetailKry = new DetailKaryawan();
        dataDetailKry.setNik(karyawan.getDetailKaryawan().getNik());
        dataDetailKry.setNpwp(karyawan.getDetailKaryawan().getNpwp());
        dataDetailKry.setKaryawan(karyawan);

        detailKaryawanRepository.save(dataDetailKry);

        return mapToKaryawanDto(karyawan);
    }

}
