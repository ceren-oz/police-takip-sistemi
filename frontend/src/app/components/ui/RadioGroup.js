"use client";
"use client";
import React from "react";

const RadioGroup = ({ label, name, value, onChange, options = [] }) => {
  return (
    <div className="mb-3">
      {label && <label className="form-label d-block">{label}</label>}
      <div>
        {options.map((opt) => (
          <div className="form-check form-check-inline" key={opt}>
            <input
              className="form-check-input"
              type="radio"
              name={name}
              value={opt}
              checked={value === opt}
              onChange={onChange}
              id={`${name}-${opt}`}
            />
            <label className="form-check-label" htmlFor={`${name}-${opt}`}>
              {opt}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RadioGroup;
