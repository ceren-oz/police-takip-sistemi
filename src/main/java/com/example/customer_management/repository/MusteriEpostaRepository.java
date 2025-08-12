package com.example.customer_management.repository;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusteriEpostaRepository extends JpaRepository<MusteriEposta, Long> {

}
