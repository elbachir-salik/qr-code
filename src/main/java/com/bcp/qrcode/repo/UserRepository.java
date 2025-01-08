package com.bcp.qrcode.repo;

import com.bcp.qrcode.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
