package com.alfian.test.repository;

import com.alfian.test.model.DetailKaryawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailKaryawanRepository extends JpaRepository<DetailKaryawan, Long> {
}
