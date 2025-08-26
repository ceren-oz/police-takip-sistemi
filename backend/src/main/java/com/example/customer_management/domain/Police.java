package com.example.customer_management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "police")
public class Police extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "musteri_id")  // foreign key column
    private Musteri musteri;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arac_id", referencedColumnName = "aracId")
    private AracBilgileri arac;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "police_teminat",
            joinColumns = @JoinColumn(name = "police_id"),
            inverseJoinColumns = @JoinColumn(name = "teminat_id")
    )
    private Set<Teminat> teminatlar = new HashSet<>();



    @Column(name = "teklif_tarihi", nullable = false)
    private LocalDate teklifTarihi;

    @Column(name = "baslangic_tarihi", nullable = false)
    private LocalDate baslangicTarihi;

    @Column(name = "bitis_tarihi", nullable = false)
    private LocalDate bitisTarihi;

    @Column(name = "prim", precision = 12, scale = 2)
    private BigDecimal prim;

    @Column(name = "risk_skoru", precision = 5, scale = 2)
    private BigDecimal riskSkoru;

    @Column(name = "muafiyet_orani", precision = 3, scale = 2)
    private BigDecimal muafiyetOrani;

   @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "police_ek_hizmet",
            joinColumns = @JoinColumn(name = "police_id"),
            inverseJoinColumns = @JoinColumn(name = "hizmet_id")
    )
    private Set<EkHizmet> ekHizmetler = new HashSet<>();

    /*@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "police_teminat",
            joinColumns = @JoinColumn(name = "police_id"),
            inverseJoinColumns = @JoinColumn(name = "teminat_id")
    )
    private Set<Teminat> teminatlar = new HashSet<>();*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public LocalDate getTeklifTarihi() {
        return teklifTarihi;
    }

    public void setTeklifTarihi(LocalDate teklifTarihi) {
        this.teklifTarihi = teklifTarihi;
    }

    public LocalDate getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDate baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public BigDecimal getPrim() {
        return prim;
    }

    public void setPrim(BigDecimal prim) {
        this.prim = prim;
    }

    public BigDecimal getRiskSkoru() {
        return riskSkoru;
    }

    public void setRiskSkoru(BigDecimal riskSkoru) {
        this.riskSkoru = riskSkoru;
    }

    public BigDecimal getMuafiyetOrani() {
        return muafiyetOrani;
    }

    public void setMuafiyetOrani(BigDecimal muafiyetOrani) {
        this.muafiyetOrani = muafiyetOrani;
    }

    public Set<EkHizmet> getEkHizmetler() {
        return ekHizmetler;
    }

    public void setEkHizmetler(Set<EkHizmet> ekHizmetler) {
        this.ekHizmetler = ekHizmetler;
    }

   /* public Set<Teminat> getTeminatlar() {
        return teminatlar;
    }

    public void setTeminatlar(Set<Teminat> teminatlar) {
        this.teminatlar = teminatlar;
    }*/

    /*public AracBilgileri getArac() {
        return arac;
    }

    public void setArac(AracBilgileri arac) {
        this.arac = arac;
    }*/


}
