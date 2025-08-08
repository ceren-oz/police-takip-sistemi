package com.example.customer_management.domain;

import com.example.customer_management.enums.Il;
import com.example.customer_management.enums.Ilce;
import com.example.customer_management.enums.Ulke;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//MusteriAdres entity'sinde şimdilik bir şey yapılmıyor
@Entity
@Table(name = "ms_musteri_adres")
public class MusteriAdres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY)
    /*fetch type lazy yada eager olabilir, gereksiz joinleri önlemek ve
     performans sorunlarına yol açmamak için lazy seçtik.*/
    /*bir musteriAdres satırı fetch edildiğinde, Musteri objesi
    hemen yüklenmeyecek*/
    //@JoinColumn(name = "musteri_id", nullable = false)
    //private Musteri musteri;

    @Column(name = "yazisma_adresi_mi")
    private Boolean yazismaAdresiMi;

    @Column(name = "adres_kisa_ad", length = 100)
    private String adresKisaAd;

    @Enumerated(EnumType.STRING)
    @Column(name = "ulke")
    private Ulke ulke;

    @Enumerated(EnumType.STRING)
    @Column(name = "il")
    private Il il;

    @Enumerated(EnumType.STRING)
    @Column(name = "ilce")
    private Ilce ilce;

    @Column(name = "cadde", length = 100)
    private String cadde;

    @Column(name = "sokak", length = 100)
    private String sokak;

    @Column(name = "apartman_adi", length = 100)
    private String apartmanAdi;

    @Column(name = "daire_no", length = 10)
    private String daireNo;

    @Column(name = "posta_kodu", length = 10)
    private String postaKodu;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @Column(name = "guncelleme_tarihi")
    private LocalDateTime guncellemeTarihi;


}
