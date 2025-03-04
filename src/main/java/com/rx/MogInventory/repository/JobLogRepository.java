package com.rx.MogInventory.repository;

import com.rx.MogInventory.entity.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface JobLogRepository extends JpaRepository<JobLog,Integer> {
    @Query(value = "SELECT COUNT(*) FROM job_log WHERE TRUNC(date) = :date", nativeQuery = true)
    Integer existsLogByDate(@Param("date") LocalDate date);

    
}
