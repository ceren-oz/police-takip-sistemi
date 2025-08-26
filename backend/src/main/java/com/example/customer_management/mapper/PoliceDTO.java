package com.example.customer_management.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class PoliceDTO {

    private Long id;
    private String musteriId;  // sadece ID dönecek
    private Long aracId;
    private LocalDate teklifTarihi;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private BigDecimal prim;
    private BigDecimal riskSkoru;
    private BigDecimal muafiyetOrani;
    private Set<Long> ekHizmetlerIds;  // ManyToMany sadece ID listesi
    private Set<Long> teminatlarIds;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMusteriId() { return musteriId; }
    public void setMusteriId(String musteriId) { this.musteriId = musteriId; }

    public LocalDate getTeklifTarihi() { return teklifTarihi; }
    public void setTeklifTarihi(LocalDate teklifTarihi) { this.teklifTarihi = teklifTarihi; }

    public LocalDate getBaslangicTarihi() { return baslangicTarihi; }
    public void setBaslangicTarihi(LocalDate baslangicTarihi) { this.baslangicTarihi = baslangicTarihi; }

    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public void setBitisTarihi(LocalDate bitisTarihi) { this.bitisTarihi = bitisTarihi; }

    public BigDecimal getPrim() { return prim; }
    public void setPrim(BigDecimal prim) { this.prim = prim; }

    public BigDecimal getRiskSkoru() { return riskSkoru; }
    public void setRiskSkoru(BigDecimal riskSkoru) { this.riskSkoru = riskSkoru; }

    public BigDecimal getMuafiyetOrani() { return muafiyetOrani; }
    public void setMuafiyetOrani(BigDecimal muafiyetOrani) { this.muafiyetOrani = muafiyetOrani; }

    public Set<Long> getEkHizmetlerIds() { return ekHizmetlerIds; }
    public void setEkHizmetlerIds(Set<Long> ekHizmetlerIds) { this.ekHizmetlerIds = ekHizmetlerIds; }

    public Long getAracId() {
        return aracId;
    }

    public void setAracId(Long aracId) {
        this.aracId = aracId;
    }

    public Set<Long> getTeminatlarIds() {
        return teminatlarIds;
    }

    public void setTeminatlarIds(Set<Long> teminatlarIds) {
        this.teminatlarIds = teminatlarIds;
    }
}
