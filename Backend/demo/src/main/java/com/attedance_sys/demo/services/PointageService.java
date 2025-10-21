package com.attedance_sys.demo.services;

import com.attedance_sys.demo.Repositories.EmployeeRepository;
import com.attedance_sys.demo.Repositories.PointageRepository;
import com.attedance_sys.demo.entity.Employee;
import com.attedance_sys.demo.entity.Pointage;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PointageService {
    private final PointageRepository pointageRepository;
    private final EmployeeRepository employeeRepository;

    public PointageService(PointageRepository pointageRepository, EmployeeRepository employeeRepository) {
        this.pointageRepository = pointageRepository;
        this.employeeRepository=employeeRepository;
    }

    public List<Pointage> findAll() {
        return pointageRepository.findAll();
    }
    public Pointage addUser(Pointage pointage) {
        return pointageRepository.save(pointage);
    }
    public Pointage getPointageById(Long id){
        return pointageRepository.findById(id).get();
    }

    public Pointage addPointage(Long id,Pointage pointage) {
        if(employeeRepository.existsById(id)){
        Employee employee=employeeRepository.findById(id).get();
        pointage.setEmployee(employee);
        return pointageRepository.save(pointage);
    }else return  null;
    }
}

