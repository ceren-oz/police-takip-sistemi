package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;

public class MusteriEpostaDTO {

    private Long id;

    private String musteriId;

    private String epostaAdresi;

    private Boolean etkIzniVarMi;

    private Boolean varsayilanMi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }*/

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

    public String getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(String musteriId) {
        this.musteriId = musteriId;
    }
}
