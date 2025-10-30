package com.attedance_sys.demo.Repositories;

import com.attedance_sys.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
