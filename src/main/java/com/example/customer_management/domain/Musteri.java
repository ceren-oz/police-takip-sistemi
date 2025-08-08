package com.example.customer_management.domain;

import com.example.customer_management.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//şimdilik test için
@Entity
@Table(name = "ms_musteri")
public class Musteri {

    @Id
    //@Column(length = 5)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //test amaçlı, sonra değişecek
    private int id;  // mXXXX or sXXXX

    @Enumerated(EnumType.STRING)
    @Column(name = "mukellef_turu", nullable = true)
    private MukellefTuru mukellefTuru;

    @Column(name = "ad", length = 100)
    private String ad;

    @Column(name = "soyad", length = 100)
    private String soyad;


    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Cinsiyet cinsiyet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MukellefTuru getMukellefTuru() {
        return mukellefTuru;
    }

    public void setMukellefTuru(MukellefTuru mukellefTuru) {
        this.mukellefTuru = mukellefTuru;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public Cinsiyet getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(Cinsiyet cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
/* @Column(name = "ana_ad", length = 100)
    private String anaAd;

    @Column(name = "baba_ad", length = 100)
    private String babaAd;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "dogum_yeri", length = 100)
    private String dogumYeri;*/

   /* @Enumerated(EnumType.STRING)
    @Column(name = "meslek")
    private Meslek meslek;

    @Enumerated(EnumType.STRING)
    @Column(name = "egitim_durumu")
    private EgitimDurumu egitimDurumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "uyruk")
    private Uyruk uyruk;

    @Enumerated(EnumType.STRING)
    @Column(name = "ulke")
    private Ulke ulke;

    @Column(name = "tc_no", length = 11)
    private String tcNo;

    @Column(name = "vergi_no", length = 20)
    private String vergiNo;

    @Column(name = "sirket_unvani", length = 255)
    private String sirketUnvani;

    @Enumerated(EnumType.STRING)
    @Column(name = "sirket_turu")
    private SirketTuru sirketTuru;

    @Enumerated(EnumType.STRING)
    @Column(name = "sektor")
    private Sektor sektor;

    @Column(name = "ozel_musteri_mi")
    private Boolean ozelMusteriMi;

    @Column(name = "sgk_kullaniyor_mu")
    private Boolean sgkKullaniyorMu;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;*/

}
