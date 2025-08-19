import React from "react";
import SelectInput from "../ui/SelectInput";
import RadioGroup from "../ui/RadioGroup";

const CommonFields = ({ formData, onChange }) => {
  return (
    <>
      <SelectInput
        name="nationality"
        label="Nationality"
        value={formData.nationality || "TC"}
        onChange={onChange}
        options={["TC"]}
      />
      <SelectInput
        name="country"
        label="Country"
        value={formData.country || "TURKIYE"}
        onChange={onChange}
        options={["TURKIYE"]}
      />
      <RadioGroup
        name="usesSGK"
        label="Uses SGK?"
        value={formData.usesSGK || "No"}
        onChange={onChange}
        options={["Yes", "No"]}
      />
      <RadioGroup
        name="specialCustomer"
        label="Is Special Customer?"
        value={formData.specialCustomer || "No"}
        onChange={onChange}
        options={["Yes", "No"]}
      />
    </>
  );
};

export default CommonFields;
