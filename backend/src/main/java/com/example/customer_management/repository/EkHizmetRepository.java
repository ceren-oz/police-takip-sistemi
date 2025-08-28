package com.example.customer_management.repository;

import com.example.customer_management.domain.EkHizmet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EkHizmetRepository extends JpaRepository<EkHizmet, Long> {

    @Query("SELECT e FROM EkHizmet e JOIN e.policeSet p WHERE p.id = :policeId")
    List<EkHizmet> findAllByPoliceId(@Param("policeId") Long policeId);
}
