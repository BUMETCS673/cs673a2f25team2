package com.team2.health.repository;

import com.team2.health.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    // Derived query using relation field
    List<HealthData> findByUser_Id(Long userId);
}

