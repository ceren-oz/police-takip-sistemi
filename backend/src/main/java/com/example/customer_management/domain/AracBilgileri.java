package com.example.customer_management.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "police_arac")
public class AracBilgileri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aracId;

    @OneToOne(mappedBy = "arac")
    private Police police;

    @Column(name = "plaka_no", nullable = false, unique = true, length = 15)
    private String plakaNo;

    @Column(name = "kullanim_sekli", nullable = false)
    @Enumerated(EnumType.STRING)
    private KullanimSekli kullanimSekli;

    @Column(name = "marka_model", nullable = false, length = 100)
    private String markaModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "arac_tipi", nullable = false)
    private AracTipi aracTipi;

    @Column(name = "model_yili", nullable = false)
    private Integer modelYili;

    @Column(name = "motor_hacmi")
    private Integer motorHacmi;

    @Enumerated(EnumType.STRING)
    @Column(name = "yakit_turu")
    private YakitTuru yakitTuru;

    @Column(name = "sasi_no", unique = true, length = 30)
    private String sasiNo;

    @Column(name = "arac_degeri", precision = 12, scale = 2)
    private BigDecimal aracDegeri;

    @Column(name = "hasar_sayisi")
    private Integer hasarSayisi;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;

    // --- Enum tanımları ---
    public enum KullanimSekli { BIREYSEL, TICARI, TAKSI }
    public enum AracTipi { OTOMOBIL, SUV, KAMYONET, MOTOSIKLET, MINIBUS, OTOBUS }
    public enum YakitTuru { BENZIN, DIZEL, ELEKTRIK, HIBRIT, LPG }

    // --- Getter - Setter ---
    public Long getAracId() { return aracId; }
    public void setAracId(Long aracId) { this.aracId = aracId; }

    public String getPlakaNo() { return plakaNo; }
    public void setPlakaNo(String plakaNo) { this.plakaNo = plakaNo; }

    public KullanimSekli getKullanimSekli() { return kullanimSekli; }
    public void setKullanimSekli(KullanimSekli kullanimSekli) { this.kullanimSekli = kullanimSekli; }

    public String getMarkaModel() { return markaModel; }
    public void setMarkaModel(String markaModel) { this.markaModel = markaModel; }

    public AracTipi getAracTipi() { return aracTipi; }
    public void setAracTipi(AracTipi aracTipi) { this.aracTipi = aracTipi; }

    public Integer getModelYili() { return modelYili; }
    public void setModelYili(Integer modelYili) { this.modelYili = modelYili; }

    public Integer getMotorHacmi() { return motorHacmi; }
    public void setMotorHacmi(Integer motorHacmi) { this.motorHacmi = motorHacmi; }

    public YakitTuru getYakitTuru() { return yakitTuru; }
    public void setYakitTuru(YakitTuru yakitTuru) { this.yakitTuru = yakitTuru; }

    public String getSasiNo() { return sasiNo; }
    public void setSasiNo(String sasiNo) { this.sasiNo = sasiNo; }

    public BigDecimal getAracDegeri() { return aracDegeri; }
    public void setAracDegeri(BigDecimal aracDegeri) { this.aracDegeri = aracDegeri; }

    public Integer getHasarSayisi() { return hasarSayisi; }
    public void setHasarSayisi(Integer hasarSayisi) { this.hasarSayisi = hasarSayisi; }

    public LocalDateTime getOlusturulmaTarihi() { return olusturulmaTarihi; }
    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) { this.olusturulmaTarihi = olusturulmaTarihi; }

    public LocalDateTime getGuncellemeTarihi() { return guncellemeTarihi; }
    public void setGuncellemeTarihi(LocalDateTime guncellemeTarihi) { this.guncellemeTarihi = guncellemeTarihi; }
}
