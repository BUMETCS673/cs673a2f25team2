package com.team2.health.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "health_data")
public class HealthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String dataType;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false)
    private Instant recordTime = Instant.now();

    @Column(length = 1000)
    private String notes;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Instant getRecordTime() { return recordTime; }
    public void setRecordTime(Instant recordTime) { this.recordTime = recordTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

