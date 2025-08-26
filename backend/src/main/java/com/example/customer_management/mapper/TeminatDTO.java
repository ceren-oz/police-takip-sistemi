package com.example.customer_management.mapper;

import com.example.customer_management.enums.TeminatTuru;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TeminatDTO {
    private Long teminatId;
    private TeminatTuru teminatTuru;
    private BigDecimal teminatBedeli;
    private LocalDateTime olusturulmaTarihi;
    private LocalDateTime guncellemeTarihi;

    // --- Getter - Setter ---
    public Long getTeminatId() { return teminatId; }
    public void setTeminatId(Long teminatId) { this.teminatId = teminatId; }

    public TeminatTuru getTeminatTuru() { return teminatTuru; }
    public void setTeminatTuru(TeminatTuru teminatTuru) { this.teminatTuru = teminatTuru; }

    public BigDecimal getTeminatBedeli() { return teminatBedeli; }
    public void setTeminatBedeli(BigDecimal teminatBedeli) { this.teminatBedeli = teminatBedeli; }

    public LocalDateTime getOlusturulmaTarihi() { return olusturulmaTarihi; }
    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) { this.olusturulmaTarihi = olusturulmaTarihi; }

    public LocalDateTime getGuncellemeTarihi() { return guncellemeTarihi; }
    public void setGuncellemeTarihi(LocalDateTime guncellemeTarihi) { this.guncellemeTarihi = guncellemeTarihi; }
}
