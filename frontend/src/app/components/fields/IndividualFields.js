"use client";
import React from "react";
import TextInput from "../ui/TextInput";
import SelectInput from "../ui/SelectInput";

const IndividualFields = ({ formData, onChange, errors }) => {
  return (
    <>
      <TextInput
        name="tcNumber"
        label="TC No"
        value={formData.tcNumber || ""}
        onChange={onChange}
        error={errors.tcNumber}
      />
      <TextInput
        name="firstName"
        label="Ad"
        value={formData.firstName || ""}
        onChange={onChange}
        error={errors.firstName}
      />
      <TextInput
        name="lastName"
        label="Soyad"
        value={formData.lastName || ""}
        onChange={onChange}
        error={errors.lastName}
      />
      <SelectInput
        name="gender"
        label="Cinsiyet"
        value={formData.gender || ""}
        onChange={onChange}
        options={["ERKEK", "KADIN"]}
      />
      <TextInput
        name="motherName"
        label="Anne Adı"
        value={formData.motherName || ""}
        onChange={onChange}
      />
      <TextInput
        name="fatherName"
        label="Baba Adı"
        value={formData.fatherName || ""}
        onChange={onChange}
      />
      <TextInput
        type="date"
        name="dateOfBirth"
        label="Doğum Tarihi"
        value={formData.dateOfBirth || ""}
        onChange={onChange}
      />
      <TextInput
        name="placeOfBirth"
        label="Doğum Yeri"
        value={formData.placeOfBirth || ""}
        onChange={onChange}
      />
      <SelectInput
        name="job"
        label="Meslek"
        value={formData.job || ""}
        onChange={onChange}
        options={[    
          "AKADEMİSYEN",
          "AŞÇI",
          "AVUKAT",
          "BANKACI",
          "ÇİFTÇİ",
          "DİŞ_HEKİMİ",
          "DOKTOR",
          "ECZACI",
          "ELEKTRİKÇİ",
          "GAZETECİ",
          "HAKİM",
          "HEMŞİRE",
          "İKTİSATÇI",
          "KAPTAN",
          "KUAFÖR",
          "MİMAR",
          "MUHASEBECİ",
          "MÜHENDİS",
          "MÜZİSYEN",
          "ÖĞRETMEN",
          "POLİS",
          "PSİKOLOG",
          "SAVCI",
          "SEKRETER",
          "ŞOFÖR",
          "TEKNİSYEN",
          "TERZİ",
          "VETERİNER",
          "YAZILIMCI",
          "YÖNETİCİ"
      ]}
      />
      <SelectInput
        name="educationStatus"
        label="Eğitim Durumu"
        value={formData.educationStatus || ""}
        onChange={onChange}
        options={["İLKOĞRETIM", "LİSE", "ÖNLİSANS","LİSANS", "LİSANSÜSTÜ"]}
      />
    </>
  );
};

export default IndividualFields;
