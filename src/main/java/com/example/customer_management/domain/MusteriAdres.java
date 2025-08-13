package com.example.customer_management.domain;

import com.example.customer_management.enums.Il;
import com.example.customer_management.enums.Ilce;
import com.example.customer_management.enums.Ulke;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//MusteriAdres entity'sinde şimdilik bir şey yapılmıyor
@Entity
@Table(name = "ms_musteri_adres")
public class MusteriAdres extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "adresler")
    @JsonIgnore
    private List<Musteri> musteriler = new ArrayList<>();

    @Column(name = "yazisma_adresi_mi")
    private Boolean yazismaAdresiMi;

    @Column(name = "adres_kisa_ad", length = 100)
    private String adresKisaAd;

    @Enumerated(EnumType.STRING)
    @Column(name = "ulke")
    private Ulke ulke;

    @Enumerated(EnumType.STRING)
    @Column(name = "il")
    private Il il;

    @Enumerated(EnumType.STRING)
    @Column(name = "ilce")
    private Ilce ilce;

    @Column(name = "cadde", length = 100)
    private String cadde;

    @Column(name = "sokak", length = 100)
    private String sokak;

    @Column(name = "apartman_adi", length = 100)
    private String apartmanAdi;

    @Column(name = "daire_no", length = 10)
    private String daireNo;

    @Column(name = "posta_kodu", length = 10)
    private String postaKodu;

   /* @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Musteri> getMusteriler() {
        return musteriler;
    }

    public void setMusteriler(List<Musteri> musteriler) {
        this.musteriler = musteriler;
    }

    public Boolean getYazismaAdresiMi() {
        return yazismaAdresiMi;
    }

    public void setYazismaAdresiMi(Boolean yazismaAdresiMi) {
        this.yazismaAdresiMi = yazismaAdresiMi;
    }

    public String getAdresKisaAd() {
        return adresKisaAd;
    }

    public void setAdresKisaAd(String adresKisaAd) {
        this.adresKisaAd = adresKisaAd;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public Il getIl() {
        return il;
    }

    public void setIl(Il il) {
        this.il = il;
    }

    public Ilce getIlce() {
        return ilce;
    }

    public void setIlce(Ilce ilce) {
        this.ilce = ilce;
    }

    public String getCadde() {
        return cadde;
    }

    public void setCadde(String cadde) {
        this.cadde = cadde;
    }

    public String getSokak() {
        return sokak;
    }

    public void setSokak(String sokak) {
        this.sokak = sokak;
    }

    public String getApartmanAdi() {
        return apartmanAdi;
    }

    public void setApartmanAdi(String apartmanAdi) {
        this.apartmanAdi = apartmanAdi;
    }

    public String getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(String daireNo) {
        this.daireNo = daireNo;
    }

    public String getPostaKodu() {
        return postaKodu;
    }

    public void setPostaKodu(String postaKodu) {
        this.postaKodu = postaKodu;
    }

   /* public LocalDateTime getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public LocalDateTime getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public void setGuncellemeTarihi(LocalDateTime guncellemeTarihi) {
        this.guncellemeTarihi = guncellemeTarihi;
    }*/



}
