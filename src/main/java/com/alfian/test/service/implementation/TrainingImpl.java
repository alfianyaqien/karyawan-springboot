package com.alfian.test.service.implementation;

import com.alfian.test.model.Training;
import com.alfian.test.repository.TrainingRepository;
import com.alfian.test.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TrainingImpl implements TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Training training) {
        Training updateTraining = trainingRepository.getById(training.getId());
        updateTraining.setTema(training.getTema());
        updateTraining.setPengajar(training.getPengajar());
        updateTraining.setUpdated_date(new Date());
        return trainingRepository.save(updateTraining);
    }

    @Override
    public Page<Training> getAllTraining(int size, int page) {
        Pageable showData = PageRequest.of(page, size);
        return trainingRepository.findAll(showData);
    }

    @Override
    public Training getTrainingById(Long id) {
        Optional<Training> training = trainingRepository.findById(id);
        if (training.isPresent()) {
            return training.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Training deleteTraining(Training training) {
        Training checkIdTraining = trainingRepository.getById(training.getId());
        checkIdTraining.setDeleted_date(new Date());
        return trainingRepository.save(checkIdTraining);
    }

}
