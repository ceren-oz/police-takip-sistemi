"use client";
import React from "react";
import TextInput from "../ui/TextInput";
import SelectInput from "../ui/SelectInput";

const CorporateFields = ({ formData, onChange, errors }) => {
  return (
    <>
      <TextInput
        name="taxNumber"
        label="Tax Number"
        value={formData.taxNumber || ""}
        onChange={onChange}
        error={errors.taxNumber}
      />
      <TextInput
        name="companyName"
        label="Company Name"
        value={formData.companyName || ""}
        onChange={onChange}
        error={errors.companyName}
      />
      <SelectInput
        name="companyType"
        label="Company Type"
        value={formData.companyType || ""}
        onChange={onChange}
        options={["ANONIM", "LIMITED", "VAKIF"]}
      />
      <SelectInput
        name="businessSector"
        label="Business Sector"
        value={formData.businessSector || ""}
        onChange={onChange}
        options={["BILISIM", "FINANS", "GIDA"]}
      />
    </>
  );
};

export default CorporateFields;
