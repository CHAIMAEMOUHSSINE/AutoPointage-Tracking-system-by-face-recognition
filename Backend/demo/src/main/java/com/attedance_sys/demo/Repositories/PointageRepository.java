package com.attedance_sys.demo.Repositories;

import com.attedance_sys.demo.entity.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointageRepository extends JpaRepository<Pointage, Long> {
    Long id(Long id);
}
