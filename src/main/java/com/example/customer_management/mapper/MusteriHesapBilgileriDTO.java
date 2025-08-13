package com.example.customer_management.mapper;

public class MusteriHesapBilgileriDTO {
    //Bunlar entity’nin alanlarıyla eşleşen basit veri tipleri.

    private Long id;
    private String musteriId;
    // musteriId burada sadece müşterinin ID’sini taşır, tüm Musteri objesini değil.

    private String bankaAdi;
    private String bankaSubeKodu;
    private String bankaSubeAdi;
    private String ibanNumarasi;

//GETTER SETTER YAZ!!!!!!!!!!!1LOMBOK İLE
public Long getId() {
    return id;
}

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(String musteriId) {
        this.musteriId = musteriId;
    }

    public String getBankaAdi() {
        return bankaAdi;
    }

    public void setBankaAdi(String bankaAdi) {
        this.bankaAdi = bankaAdi;
    }

    public String getBankaSubeKodu() {
        return bankaSubeKodu;
    }

    public void setBankaSubeKodu(String bankaSubeKodu) {
        this.bankaSubeKodu = bankaSubeKodu;
    }

    public String getBankaSubeAdi() {
        return bankaSubeAdi;
    }

    public void setBankaSubeAdi(String bankaSubeAdi) {
        this.bankaSubeAdi = bankaSubeAdi;
    }

    public String getIbanNumarasi() {
        return ibanNumarasi;
    }

    public void setIbanNumarasi(String ibanNumarasi) {
        this.ibanNumarasi = ibanNumarasi;
    }
}
