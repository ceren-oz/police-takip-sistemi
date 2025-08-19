"use client";
"use client";
import React from "react";

const TextInput = ({ label, name, value, onChange, type = "text", placeholder = "", error }) => {
  return (
    <div className="mb-3">
      {label && <label htmlFor={name} className="form-label">{label}</label>}
      <input
        id={name}
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className={`form-control${error ? " is-invalid" : ""}`}
      />
      {error && <div className="invalid-feedback">{error}</div>}
    </div>
  );
};

export default TextInput;
