package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.enums.Il;
import com.example.customer_management.enums.Ilce;

import java.util.ArrayList;
import java.util.List;

public class MusteriAdresDTO {

    private Long id;

    private Boolean yazismaAdresiMi;

    private String adresKisaAd;

    private Il il;

    private Ilce ilce;

    private String cadde;

    private String sokak;

    private String apartmanAdi;

    private String daireNo;


    //private List<Musteri> musteriler = new ArrayList<>();
    private List<String> musteriIds = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /*public List<Musteri> getMusteriler() {
        return musteriler;
    }

    public void setMusteriler(List<Musteri> musteriler) {
        this.musteriler = musteriler;
    }*/

    public List<String> getMusteriIds() {
        return musteriIds;
    }

    public void setMusteriIds(List<String> musteriIds) {
        this.musteriIds = musteriIds;
    }
}
