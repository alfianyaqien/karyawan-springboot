package com.alfian.test.repository;

import com.alfian.test.model.KaryawanTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanTrainingRepository extends JpaRepository<KaryawanTraining, Long> {
}
