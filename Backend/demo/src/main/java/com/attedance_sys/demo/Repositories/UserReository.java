package com.attedance_sys.demo.Repositories;

import com.attedance_sys.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReository extends JpaRepository<User,Long> {
    public List<User> findAllByEmail(String email);
}
