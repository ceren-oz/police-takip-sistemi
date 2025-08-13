package com.example.customer_management.mapper;

import com.example.customer_management.enums.TelefonTipi;
public class MusteriTelefonDTO {

    private Long id; //telefon kaydi id si
    private String musteriId;// musteri tablosundaki id
    private Boolean iletisimTelefonuMu;
    private TelefonTipi telefonTipi;
    private String ulkeKodu;
    private String alanKodu;
    private String telefonNumarasi;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(String musteriId) {
        this.musteriId = musteriId;
    }

    public Boolean getIletisimTelefonuMu() {
        return iletisimTelefonuMu;
    }

    public void setIletisimTelefonuMu(Boolean iletisimTelefonuMu) {
        this.iletisimTelefonuMu = iletisimTelefonuMu;
    }

    public TelefonTipi getTelefonTipi() {
        return telefonTipi;
    }

    public void setTelefonTipi(TelefonTipi telefonTipi) {
        this.telefonTipi = telefonTipi;
    }

    public String getUlkeKodu() {
        return ulkeKodu;
    }

    public void setUlkeKodu(String ulkeKodu) {
        this.ulkeKodu = ulkeKodu;
    }

    public String getAlanKodu() {
        return alanKodu;
    }

    public void setAlanKodu(String alanKodu) {
        this.alanKodu = alanKodu;
    }

    public String getTelefonNumarasi() {
        return telefonNumarasi;
    }

    public void setTelefonNumarasi(String telefonNumarasi) {
        this.telefonNumarasi = telefonNumarasi;
    }
}

