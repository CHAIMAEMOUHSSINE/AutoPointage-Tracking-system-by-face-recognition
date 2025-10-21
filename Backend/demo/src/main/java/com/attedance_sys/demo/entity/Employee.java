package com.attedance_sys.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="employees")
public class Employee extends User{
    private String Departement;
    @Lob
    private byte[] faceVector;
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Pointage> pointages;


}
