package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.enums.Cinsiyet;
import com.example.customer_management.enums.MukellefTuru;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MusteriDTO {

    private String id;  // mXXXX or sXXXX

    private MukellefTuru mukellefTuru;//gercek +tuzel

    private String ad;//gercek

    private String soyad;//gercek

    private Cinsiyet cinsiyet;//gercek

    private List<MusteriAdres> adresler = new ArrayList<>();
    private List<MusteriEposta> epostalar = new ArrayList<>();


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

    public List<Long> getAdresIds() {
        if (adresler == null) {
            return new ArrayList<>();
        }
        return adresler.stream()
                .map(MusteriAdres::getId)
                .collect(Collectors.toList());
    }

}
