package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.enums.*;
import com.example.customer_management.validation.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@MukellefTuruConstraint
public class MusteriDTO {

    private String id;  // mXXXX or sXXXX

    @NotNull(message = "Mükellef türü zorunludur")
    private MukellefTuru mukellefTuru; // gercek + tuzel

    private String ad; // gercek

    private String soyad; // gercek

    private Cinsiyet cinsiyet; // gercek

    private String anaAd;//gercek

    private String babaAd;//gercek

    private LocalDate dogumTarihi;//gercek

    private String dogumYeri;//gercek

    private Meslek meslek;//gercek

    private EgitimDurumu egitimDurumu;//gercek

    private Uyruk uyruk;//gercek + tuzel

    private Ulke ulke;//gercek + tuzel

    private String tcNo;//gercek

    private String vergiNo;//tuzel

    private String sirketUnvani;//tuzel

    private SirketTuru sirketTuru;//tuzel

    private Sektor sektor;//tuzel

    private Boolean ozelMusteriMi;//gercek+tuzel

    private Boolean sgkKullaniyorMu;//gercek + tuzel


  /*  private List<MusteriAdres> adresler = new ArrayList<>();


    private List<MusteriEposta> epostalar = new ArrayList<>();*/

    private List<Long> adresIds = new ArrayList<>();
    private List<Long> epostaIds = new ArrayList<>();
    private List<Long> telefonIds = new ArrayList<>();
    private List<Long> hesapBilgileriIds = new ArrayList<>();



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

  /*  public List<MusteriAdres> getAdresler() {
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
    }*/

    public List<Long> getAdresIds() {
        return adresIds;
    }

    public void setAdresIds(List<Long> adresIds) {
        this.adresIds = adresIds;
    }

    public List<Long> getEpostaIds() {
        return epostaIds;
    }

    public void setEpostaIds(List<Long> epostaIds) {
        this.epostaIds = epostaIds;
    }

    public List<Long> getTelefonIds() {
        return telefonIds;
    }

    public void setTelefonIds(List<Long> telefonIds) {
        this.telefonIds = telefonIds;
    }

    public List<Long> getHesapBilgileriIds() {
        return hesapBilgileriIds;
    }

    public void setHesapBilgileriIds(List<Long> hesapBilgileriIds) {
        this.hesapBilgileriIds = hesapBilgileriIds;
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
}
