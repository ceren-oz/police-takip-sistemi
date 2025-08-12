package com.example.customer_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="ms_musteri_eposta")
//@Getter
//@Setter
public class MusteriEposta extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// E-posta kaydı ID’si (PK)

//    @ManyToOne
//    @JoinColumn(name="musteri_id",nullable=false)
//    private Musteri musteri; //FK ŞİMDİLİK YORUMDA

    @Column(name="eposta_adresi",length=254)
    private String epostaAdresi;

    @Column (name="etk_izni_var_mi")
    private Boolean etkIzniVarMi;

    @Column(name="varsayilan_mi")
    private Boolean varsayilanMi;

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

    public String getEpostaAdresi() {
        return epostaAdresi;
    }

    public void setEpostaAdresi(String epostaAdresi) {
        this.epostaAdresi = epostaAdresi;
    }

    public Boolean getEtkIzniVarMi() {
        return etkIzniVarMi;
    }

    public void setEtkIzniVarMi(Boolean etkIzniVarMi) {
        this.etkIzniVarMi = etkIzniVarMi;
    }

    public Boolean getVarsayilanMi() {
        return varsayilanMi;
    }

    public void setVarsayilanMi(Boolean varsayilanMi) {
        this.varsayilanMi = varsayilanMi;
    }



}
