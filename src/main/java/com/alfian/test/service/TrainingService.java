package com.alfian.test.service;

import com.alfian.test.model.Training;
import org.springframework.data.domain.Page;

public interface TrainingService {
    Training saveTraining(Training training);
    Training updateTraining(Training training);
    Page<Training> getAllTraining(int size, int page);
    Training getTrainingById(Long id);
    Training deleteTraining(Training training);
}
