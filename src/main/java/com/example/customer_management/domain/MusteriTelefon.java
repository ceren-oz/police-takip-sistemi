package com.example.customer_management.domain;
import com.example.customer_management.enums.TelefonTipi;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ms_musteri_telefon")
public class MusteriTelefon extends BaseEntity{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY) //GenerationType ı  eğer IDENTITY seçersek >AI
    private Long id; //telefon kaydı id'si

//    @ManyToOne
//    @JoinColumn(name="musteri_id",nullable=false)
//    private Musteri musteri; //FK ŞİMDİLİK YORUMDA

    @Column(name="iletisim_telefonu_mu")
    private Boolean iletisimTelefonuMu;

    @Enumerated (EnumType.STRING)
    @Column(name="telefon_tipi")
    private TelefonTipi telefonTipi;

    @Column(name="ulke_kodu", length=2) //dünya geneli 3
    private String ulkeKodu;
//ULKE enumunda sadece TR olduğu icin telefon bilgilerini ona yönelik yaptım
    @Column(name="alan_kodu",length=3) //dünya geneli max 5-değişilebilir-
    private String alanKodu;

    @Column(name = "telefon_numarasi", length = 7)
    private String telefonNumarasi;

//    @Column(name="olusturulma_tarihi")
//    private LocalDateTime olusturulmaTarihi;
//
//    @Column(name="guncelleme_tarihi")
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
