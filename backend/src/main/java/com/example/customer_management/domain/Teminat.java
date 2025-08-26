package com.example.customer_management.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "police_teminat")
public class Teminat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teminatId;

    @ManyToMany(mappedBy = "teminatlar")
    private Set<Police> policeler = new HashSet<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "teminat_turu", nullable = false)
    private TeminatTuru teminatTuru;

    @Column(name = "teminat_bedeli", precision = 12, scale = 2)
    private BigDecimal teminatBedeli;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;

    public enum TeminatTuru {
        CARPMA, YANMA, CALINMA, DOGAL_AFETLER, IHTIYARI_MALI_MESULIYET, FERDI_KAZA
    }

    // --- Getter - Setter ---
    public Long getTeminatId() { return teminatId; }
    public void setTeminatId(Long teminatId) { this.teminatId = teminatId; }

    public Police getPolice() { return police; }
    public void setPolice(Police police) { this.police = police; }

    public TeminatTuru getTeminatTuru() { return teminatTuru; }
    public void setTeminatTuru(TeminatTuru teminatTuru) { this.teminatTuru = teminatTuru; }

    public BigDecimal getTeminatBedeli() { return teminatBedeli; }
    public void setTeminatBedeli(BigDecimal teminatBedeli) { this.teminatBedeli = teminatBedeli; }

    public LocalDateTime getOlusturulmaTarihi() { return olusturulmaTarihi; }
    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) { this.olusturulmaTarihi = olusturulmaTarihi; }

    public LocalDateTime getGuncellemeTarihi() { return guncellemeTarihi; }
    public void setGuncellemeTarihi(LocalDateTime guncellemeTarihi) { this.guncellemeTarihi = guncellemeTarihi; }
}
