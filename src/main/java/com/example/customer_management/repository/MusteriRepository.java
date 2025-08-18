package com.example.customer_management.repository;

import com.example.customer_management.domain.Musteri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusteriRepository extends JpaRepository<Musteri, String> {

    Optional<Musteri> findByTcNo(String tcNo);
}
