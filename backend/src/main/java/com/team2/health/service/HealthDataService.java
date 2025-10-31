package com.team2.health.service;

import com.team2.health.entity.HealthData;
import com.team2.health.entity.User;
import com.team2.health.repository.HealthDataRepository;
import com.team2.health.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthDataService {
    private final HealthDataRepository healthDataRepository;
    private final UserRepository userRepository;

    public HealthDataService(HealthDataRepository healthDataRepository, UserRepository userRepository) {
        this.healthDataRepository = healthDataRepository;
        this.userRepository = userRepository;
    }

    // Create a new health record
    public Optional<HealthData> create(Long userId, String dataType, Double value, String unit, String notes) {
        return userRepository.findById(userId).map(user -> {
            HealthData hd = new HealthData();
            hd.setUser(user);
            hd.setDataType(dataType);
            hd.setValue(value);
            hd.setUnit(unit);
            hd.setNotes(notes);
            return healthDataRepository.save(hd);
        });
    }

    // List records by user id
    public List<HealthData> listByUser(Long userId) {
        return healthDataRepository.findByUser_Id(userId);
    }

    // List all records
    public List<HealthData> listAll() {
        return healthDataRepository.findAll();
    }
}
