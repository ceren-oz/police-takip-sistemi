"use client";
import React from "react";
import TextInput from "../ui/TextInput";
import SelectInput from "../ui/SelectInput";

const IndividualFields = ({ formData, onChange, errors }) => {
  return (
    <>
      <TextInput
        name="tcNumber"
        label="TC Number"
        value={formData.tcNumber || ""}
        onChange={onChange}
        error={errors.tcNumber}
      />
      <TextInput
        name="firstName"
        label="First Name"
        value={formData.firstName || ""}
        onChange={onChange}
        error={errors.firstName}
      />
      <TextInput
        name="lastName"
        label="Last Name"
        value={formData.lastName || ""}
        onChange={onChange}
        error={errors.lastName}
      />
      <SelectInput
        name="gender"
        label="Gender"
        value={formData.gender || ""}
        onChange={onChange}
        options={["ERKEK", "KADIN"]}
      />
      <TextInput
        name="motherName"
        label="Mother's Name"
        value={formData.motherName || ""}
        onChange={onChange}
      />
      <TextInput
        name="fatherName"
        label="Father's Name"
        value={formData.fatherName || ""}
        onChange={onChange}
      />
      <TextInput
        type="date"
        name="dateOfBirth"
        label="Date of Birth"
        value={formData.dateOfBirth || ""}
        onChange={onChange}
      />
      <TextInput
        name="placeOfBirth"
        label="Place of Birth"
        value={formData.placeOfBirth || ""}
        onChange={onChange}
      />
      <SelectInput
        name="job"
        label="Job"
        value={formData.job || ""}
        onChange={onChange}
        options={["AVUKAT", "BANKACI"]}
      />
      <SelectInput
        name="educationStatus"
        label="Education Status"
        value={formData.educationStatus || ""}
        onChange={onChange}
        options={["ILKOGRETIM", "LISE", "LISANS"]}
      />
    </>
  );
};

export default IndividualFields;
