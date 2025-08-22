"use client";
import React from "react";
import TextInput from "../ui/TextInput";
import SelectInput from "../ui/SelectInput";

const CorporateFields = ({ formData, onChange, errors }) => {
  return (
    <>
      <TextInput
        name="taxNumber"
        label="Vergi No"
        value={formData.taxNumber || ""}
        onChange={onChange}
        error={errors.taxNumber}
      />
      <TextInput
        name="companyName"
        label="Şirket Unvanı"
        value={formData.companyName || ""}
        onChange={onChange}
        error={errors.companyName}
      />
      <SelectInput
        name="companyType"
        label="Şirket Türü"
        value={formData.companyType || ""}
        onChange={onChange}
        options={[    
          "ADİ",
          "KOLLEKTİF",
          "ADİ_KOMANDİT",
          "ESHAMLI_KOMANDİT",
          "LIMITED", 
          "ANONIM",
          "KOOPERATİF",
          "VAKIF",
          "TAO",
          "DİĞER"
      ]}
      />
      <SelectInput
        name="businessSector"
        label="Sektör"
        value={formData.businessSector || ""}
        onChange={onChange}
        options={[    
          "AĞIR_SANAYİ",
          "BANKACILIK",
          "BİLİŞİM",
          "ENERJİ",
          "EĞİTİM",
          "GIDA",
          "GAYRİMENKUL",
          "GÜVENLİK_HİZMETLERİ",
          "HAVACILIK",
          "HAYVANCILIK",
          "OTELCİLİK",
          "İKİNCİ_EL_TİCARET",
          "İLK_SEKTÖR",
          "İKİNCİ_SEKTÖR",
          "İLETİŞİM",
          "İNŞAAT",
          "KAMU_HİZMETİ",
          "KİMYA",
          "KOZMETİK_TEMİZLİK",
          "LOJİSTİK",
          "MAKİNE_EKİPMAN",
          "MEDYA",
          "OTOMOTİV",
          "PERAKENDE",
          "SAĞLIK",
          "SAVUNMA_SANAYİİ",
          "SİGORTACILIK",
          "TEKNOLOJİ",
          "TEKSTİL_GİYİM",
          "TURİZM",
          "ULAŞTIRMA",
          "VETERİNERLİK"
      ]}
      />
    </>
  );
};

export default CorporateFields;
