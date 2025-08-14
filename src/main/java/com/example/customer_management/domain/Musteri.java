package com.example.customer_management.domain;

import com.example.customer_management.enums.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


   /* @Column(name = "ana_ad", length = 100)
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

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;*/

}
