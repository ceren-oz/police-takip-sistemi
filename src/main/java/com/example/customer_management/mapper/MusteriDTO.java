package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.enums.Cinsiyet;
import com.example.customer_management.enums.MukellefTuru;

import java.util.ArrayList;
import java.util.List;

public class MusteriDTO {

    private String id;  // mXXXX or sXXXX

    private MukellefTuru mukellefTuru; // gercek + tuzel

    private String ad; // gercek

    private String soyad; // gercek

    private Cinsiyet cinsiyet; // gercek


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
}
