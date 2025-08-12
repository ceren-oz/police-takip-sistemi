package com.example.customer_management.domain;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="ms_msuteri_hesap_bilgileri")
public class MusteriHesapBilgileri extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Hesap bilgisi kaydı ID’si (PK)

    @ManyToOne
    @JoinColumn(name="musteri_id",nullable=false)
    private Musteri musteri; //FK

    //@Getter
    @Column(name="banka_adi,length=100")
    private String bankaAdi;

    @Column(name = "banka_sube_kodu", length = 20)
    private String bankaSubeKodu;

    @Column(name = "banka_sube_adi", length = 100)
    private String bankaSubeAdi;

    @Column(name = "iban_numarasi", length = 34)
    private String ibanNumarasi;

//    @Column(name="olusturulma_tarihi")
//    private LocalDateTime olusturulmaTarihi;
//
//    @Column(name = "guncelleme_tarihi")
//    private LocalDateTime guncellemeTarihi;


    public Long getId() {
        return id;
    }

//public Musteri getMusteri() {
//    return musteri;
//}
//
//public void setMusteri(Musteri musteri) {
//    this.musteri = musteri;
//}

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
