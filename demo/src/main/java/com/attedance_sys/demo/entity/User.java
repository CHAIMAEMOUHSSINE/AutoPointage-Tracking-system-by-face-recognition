package com.attedance_sys.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    public enum  Role{
        HR,Employee
    }

}
