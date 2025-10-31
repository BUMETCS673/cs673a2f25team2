package com.team2.health.dto;

public class HealthDataResponse {
    private Long id;
    private Long userId;
    private String dataType;
    private Double value;
    private String unit;
    private String recordTime; // ISO-8601 string to avoid JavaTime serialization issues
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getRecordTime() { return recordTime; }
    public void setRecordTime(String recordTime) { this.recordTime = recordTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

