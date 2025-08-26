package com.example.customer_management.domain;

import com.example.customer_management.enums.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//şimdilik test için
@Entity
@Table(name = "ms_musteri")
public class Musteri extends BaseEntity{

    @Id
    @GeneratedValue(generator = "musteri-id-generator")
    @GenericGenerator(
            name = "musteri-id-generator",
            strategy = "com.example.customer_management.generator.MusteriIdGenerator"
    )
    @Column(length = 5)
    private String id;  // mXXXX or sXXXX
    //gercek +tuzel

    /*@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "ms_musteri_musteri_adres",
            joinColumns = @JoinColumn(name = "musteri_id"),
            inverseJoinColumns = @JoinColumn(name = "adres_id")
    )
    private List<MusteriAdres> adresler = new ArrayList<>();*/

    @OneToMany(mappedBy = "musteri", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusteriAdres> adresler = new ArrayList<>();

    @OneToMany(mappedBy = "musteri", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Police> policeler = new HashSet<>();


    @OneToMany(mappedBy = "musteri", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusteriEposta> epostalar = new ArrayList<>();

    @OneToMany(mappedBy = "musteri", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusteriHesapBilgileri> hesapBilgileri = new ArrayList<>();

    @OneToMany(mappedBy = "musteri", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusteriTelefon> telefonlar = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "mukellef_turu", nullable = true)
    private MukellefTuru mukellefTuru;//gercek +tuzel

    @Column(name = "ad", length = 100)
    private String ad;//gercek

    @Column(name = "soyad", length = 100)
    private String soyad;//gercek


    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Cinsiyet cinsiyet;//gercek

    @Column(name = "ana_ad", length = 100)
    private String anaAd;//gercek

    @Column(name = "baba_ad", length = 100)
    private String babaAd;//gercek

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;//gercek

    @Column(name = "dogum_yeri", length = 100)
    private String dogumYeri;//gercek

    @Enumerated(EnumType.STRING)
    @Column(name = "meslek")
    private Meslek meslek;//gercek

    @Enumerated(EnumType.STRING)
    @Column(name = "egitim_durumu")
    private EgitimDurumu egitimDurumu;//gercek

    @Enumerated(EnumType.STRING)
    @Column(name = "uyruk")
    private Uyruk uyruk;//gercek + tuzel

    @Enumerated(EnumType.STRING)
    @Column(name = "ulke")
    private Ulke ulke;//gercek + tuzel

    @Column(name = "tc_no", length = 11)
    private String tcNo;//gercek

    @Column(name = "vergi_no", length = 20)
    private String vergiNo;//tuzel

    @Column(name = "sirket_unvani", length = 255)
    private String sirketUnvani;//tuzel

    @Enumerated(EnumType.STRING)
    @Column(name = "sirket_turu")
    private SirketTuru sirketTuru;//tuzel

    @Enumerated(EnumType.STRING)
    @Column(name = "sektor")
    private Sektor sektor;//tuzel

    @Column(name = "ozel_musteri_mi")
    private Boolean ozelMusteriMi;//gercek+tuzel

    @Column(name = "sgk_kullaniyor_mu")
    private Boolean sgkKullaniyorMu;//gercek + tuzel



    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    }//gercek

    public void setAd(String ad) {
        this.ad = ad;
    }//gercek

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

    public List<MusteriAdres> getAdresler() {
        return adresler;
    }

    public void setAdresler(List<MusteriAdres> adresler) {
        this.adresler = adresler;
    }

    public List<MusteriEposta> getEpostalar() {
        return epostalar;
    }

    public void setEpostalar(List<MusteriEposta> epostalar) {
        this.epostalar = epostalar;
    }

    public List<MusteriHesapBilgileri> getHesapBilgileri() {
        return hesapBilgileri;
    }

    public void setHesapBilgileri(List<MusteriHesapBilgileri> hesapBilgileri) {
        this.hesapBilgileri = hesapBilgileri;
    }

    public List<MusteriTelefon> getTelefonlar() {
        return telefonlar;
    }

    public void setTelefonlar(List<MusteriTelefon> telefonlar) {
        this.telefonlar = telefonlar;
    }

    public String getAnaAd() {
        return anaAd;
    }

    public void setAnaAd(String anaAd) {
        this.anaAd = anaAd;
    }

    public String getBabaAd() {
        return babaAd;
    }

    public void setBabaAd(String babaAd) {
        this.babaAd = babaAd;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getDogumYeri() {
        return dogumYeri;
    }

    public void setDogumYeri(String dogumYeri) {
        this.dogumYeri = dogumYeri;
    }

    public Meslek getMeslek() {
        return meslek;
    }

    public void setMeslek(Meslek meslek) {
        this.meslek = meslek;
    }

    public EgitimDurumu getEgitimDurumu() {
        return egitimDurumu;
    }

    public void setEgitimDurumu(EgitimDurumu egitimDurumu) {
        this.egitimDurumu = egitimDurumu;
    }

    public Uyruk getUyruk() {
        return uyruk;
    }

    public void setUyruk(Uyruk uyruk) {
        this.uyruk = uyruk;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
    }

    public String getSirketUnvani() {
        return sirketUnvani;
    }

    public void setSirketUnvani(String sirketUnvani) {
        this.sirketUnvani = sirketUnvani;
    }

    public SirketTuru getSirketTuru() {
        return sirketTuru;
    }

    public void setSirketTuru(SirketTuru sirketTuru) {
        this.sirketTuru = sirketTuru;
    }

    public Sektor getSektor() {
        return sektor;
    }

    public void setSektor(Sektor sektor) {
        this.sektor = sektor;
    }

    public Boolean getOzelMusteriMi() {
        return ozelMusteriMi;
    }

    public void setOzelMusteriMi(Boolean ozelMusteriMi) {
        this.ozelMusteriMi = ozelMusteriMi;
    }

    public Boolean getSgkKullaniyorMu() {
        return sgkKullaniyorMu;
    }

    public void setSgkKullaniyorMu(Boolean sgkKullaniyorMu) {
        this.sgkKullaniyorMu = sgkKullaniyorMu;
    }

    public Set<Police> getPoliceler() {
        return policeler;
    }

    public void setPoliceler(Set<Police> policeler) {
        this.policeler = policeler;
    }
}
