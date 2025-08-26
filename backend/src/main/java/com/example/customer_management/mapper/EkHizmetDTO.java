package com.example.customer_management.mapper;

import com.example.customer_management.domain.Police;
import com.example.customer_management.enums.HizmetTuru;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class EkHizmetDTO {

    private Long id;

    private HizmetTuru hizmetTuru;

    private BigDecimal hizmetBedeli;

    private Set<Long> policeIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HizmetTuru getHizmetTuru() {
        return hizmetTuru;
    }

    public void setHizmetTuru(HizmetTuru hizmetTuru) {
        this.hizmetTuru = hizmetTuru;
    }

    public BigDecimal getHizmetBedeli() {
        return hizmetBedeli;
    }

    public void setHizmetBedeli(BigDecimal hizmetBedeli) {
        this.hizmetBedeli = hizmetBedeli;
    }

    public Set<Long> getPoliceIds() {
        return policeIds;
    }

    public void setPoliceIds(Set<Long> policeIds) {
        this.policeIds = policeIds;
    }
}
