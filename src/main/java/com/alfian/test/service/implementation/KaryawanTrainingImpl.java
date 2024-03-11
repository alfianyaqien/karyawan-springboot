package com.alfian.test.service.implementation;

import com.alfian.test.model.Karyawan;
import com.alfian.test.model.KaryawanTraining;
import com.alfian.test.model.Training;
import com.alfian.test.repository.KaryawanRepository;
import com.alfian.test.repository.KaryawanTrainingRepository;
import com.alfian.test.repository.TrainingRepository;
import com.alfian.test.service.KaryawanTrainingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class KaryawanTrainingImpl implements KaryawanTrainingService {
    private final TrainingRepository trainingRepository;
    private final KaryawanRepository karyawanRepository;
    private final KaryawanTrainingRepository karyawanTrainingRepository;

    @Autowired
    public KaryawanTrainingImpl(TrainingRepository trainingRepository, KaryawanRepository karyawanRepository, KaryawanTrainingRepository karyawanTrainingRepository) {
        this.trainingRepository = trainingRepository;
        this.karyawanRepository = karyawanRepository;
        this.karyawanTrainingRepository = karyawanTrainingRepository;
    }

    @Override
    public KaryawanTraining saveKaryawanTraining(KaryawanTraining karyawanTraining) {

        Optional<Karyawan> karyawan = karyawanRepository.findById(karyawanTraining.getKaryawan().getId());
        Optional<Training> training = trainingRepository.findById(karyawanTraining.getTraining().getId());
        karyawanTraining.setKaryawan(karyawan.get());
        karyawanTraining.setTraining(training.get());
        karyawanTrainingRepository.save(karyawanTraining);
        return karyawanTraining;
    }

    @Override
    public KaryawanTraining updateKaryawanTraining(KaryawanTraining karyawanTraining) {
        Optional<Karyawan> karyawan = karyawanRepository.findById(karyawanTraining.getKaryawan().getId());
        Optional<Training> training = trainingRepository.findById(karyawanTraining.getTraining().getId());

        KaryawanTraining updateKaryawanTraining = karyawanTrainingRepository.getById(karyawanTraining.getId());
        updateKaryawanTraining.setKaryawan(karyawan.get());
        updateKaryawanTraining.setTraining(training.get());
        updateKaryawanTraining.setTanggalTraining(karyawanTraining.getTanggalTraining());
        return karyawanTrainingRepository.save(updateKaryawanTraining);
    }

    @Override
    public Page<KaryawanTraining> getAllKaryawanTraining(int size, int page) {
        Pageable showData = PageRequest.of(page, size);
        return karyawanTrainingRepository.findAll(showData);
    }

    @Override
    public KaryawanTraining getKaryawanTrainingById(Long id) {
        Optional<KaryawanTraining> karyawanTraining = karyawanTrainingRepository.findById(id);
        if (karyawanTraining.isPresent()) {
            return karyawanTraining.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public KaryawanTraining deleteKaryawanTraining(KaryawanTraining karyawanTraining) {
        KaryawanTraining checkIdKaryawanTraining = karyawanTrainingRepository.getById(karyawanTraining.getId());
        checkIdKaryawanTraining.setDeleted_date(new Date());
        return karyawanTrainingRepository.save(checkIdKaryawanTraining);
    }

}
