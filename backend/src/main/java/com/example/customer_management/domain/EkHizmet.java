package com.example.customer_management.domain;

import com.example.customer_management.enums.HizmetTuru;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ek_hizmet")
public class EkHizmet extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hizmet_turu")
    private HizmetTuru hizmetTuru;

    @Column(name = "hizmet_bedeli", precision = 10, scale = 2)
    private BigDecimal hizmetBedeli;

    @ManyToMany(mappedBy = "ekHizmetler")
    private Set<Police> policeler = new HashSet<>();

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

    public Set<Police> getPoliceler() {
        return policeler;
    }

    public void setPoliceler(Set<Police> policeler) {
        this.policeler = policeler;
    }
}
