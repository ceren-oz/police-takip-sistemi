"use client";
"use client";
import React from "react";

const SelectInput = ({ label, name, value, onChange, options = [], error }) => {
  return (
    <div className="mb-3">
      {label && <label htmlFor={name} className="form-label">{label}</label>}
      <select
        id={name}
        name={name}
        value={value}
        onChange={onChange}
        className={`form-select${error ? " is-invalid" : ""}`}
      >
        <option value="">Select...</option>
        {options.map((opt) => (
          <option key={opt} value={opt}>
            {opt}
          </option>
        ))}
      </select>
      {error && <div className="invalid-feedback">{error}</div>}
    </div>
  );
};

export default SelectInput;
