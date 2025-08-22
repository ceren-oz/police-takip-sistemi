import React from "react";
import SelectInput from "../ui/SelectInput";
import RadioGroup from "../ui/RadioGroup";

const CommonFields = ({ formData, onChange }) => {
  return (
    <>
      <SelectInput
        name="nationality"
        label="Uyruk"
        value={formData.nationality || "TC"}
        onChange={onChange}
        options={["TC"]}
      />
      <SelectInput
        name="country"
        label="Ülke"
        value={formData.country || "TURKIYE"}
        onChange={onChange}
        options={["TURKIYE"]}
      />
      <RadioGroup
        name="usesSGK"
        label="SGK Kullanıyor Mu?"
        value={formData.usesSGK || "No"}
        onChange={onChange}
        options={["Evet", "Hayır"]}
      />
      <RadioGroup
        name="specialCustomer"
        label="Özel Müşteri Mi?"
        value={formData.specialCustomer || "No"}
        onChange={onChange}
        options={["Evet", "Hayır"]}
      />
    </>
  );
};

export default CommonFields;
