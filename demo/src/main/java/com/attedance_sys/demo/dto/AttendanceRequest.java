package com.attedance_sys.demo.dto;

import lombok.Data;

@Data
public class AttendanceRequest {
    // Getters & Setters
    private String employeeName;
    private String statut;

    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public void setStatut(String statut) { this.statut = statut; }}