package com.attedance_sys.demo.controllers;

import com.attedance_sys.demo.Repositories.EmployeeRepository;
import com.attedance_sys.demo.Repositories.PointageRepository;
import com.attedance_sys.demo.dto.AttendanceRequest;
import com.attedance_sys.demo.entity.Employee;
import com.attedance_sys.demo.entity.Pointage;
import com.attedance_sys.demo.services.PointageService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pointage")

public class PointageController {
    private final PointageService pointageService;

    public PointageController(PointageService pointageService) {
        this.pointageService = pointageService;
    }
    @GetMapping
    public List<Pointage> getAllPointage(){
        return pointageService.findAll();
    }
    @GetMapping("/{id}")
    public Pointage getPointageById(@PathVariable Long id){
        return pointageService.getPointageById(id);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Pointage> createPointage(@PathVariable Long id, @RequestBody Pointage pointage){
        Pointage pointage1=pointageService.addPointage(id, pointage);
        if(pointage1!=null){
        return  ResponseEntity.ok(pointage1);}
        else return ResponseEntity.notFound().build();
    }

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private EmployeeRepository employeeRepository;



    @PostMapping("/mark/json")
    public ResponseEntity<Pointage> markAttendanceJson(@RequestBody AttendanceRequest request) {
        // Vérifier si l'employé existe en base
        Employee employee = employeeRepository.findByUsername(request.getEmployeeName())
                .orElseThrow(() -> new RuntimeException("Employé introuvable: " + request.getEmployeeName()));

        // Créer un pointage
        Pointage pointage = new Pointage();
        pointage.setEmployee(employee);
        pointage.setDate(LocalDateTime.now());
        pointage.setArrivalTime(LocalDateTime.now());
        pointage.setStatut(Pointage.Statut.valueOf(request.getStatut()));

        Pointage saved = pointageRepository.save(pointage);

        return ResponseEntity.ok(saved);
    }



}
