package com.example.customer_management.mapper;

import com.example.customer_management.domain.AracBilgileri.AracTipi;
import com.example.customer_management.domain.AracBilgileri.KullanimSekli;
import com.example.customer_management.domain.AracBilgileri.YakitTuru;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AracBilgileriDTO {
    private Long aracId;
    private String plakaNo;
    private KullanimSekli kullanimSekli;
    private String markaModel;
    private AracTipi aracTipi;
    private Integer modelYili;
    private Integer motorHacmi;
    private YakitTuru yakitTuru;
    private String sasiNo;
    private BigDecimal aracDegeri;
    private Integer hasarSayisi;
    private LocalDateTime olusturulmaTarihi;
    private LocalDateTime guncellemeTarihi;

    // --- Getter & Setter ---
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
