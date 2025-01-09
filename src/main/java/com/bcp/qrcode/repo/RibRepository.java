package com.bcp.qrcode.repo;

import com.bcp.qrcode.entities.Rib;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RibRepository extends JpaRepository<Rib, Long> {
    Optional<Rib> findByUserId(Long userId);
}
