"use client";
import React, { useState } from "react";
import IndividualFields from "./fields/IndividualFields";
import CorporateFields from "./fields/CorporateFields";
import CommonFields from "./fields/CommonFields";

const GeneralInformationForm = () => {
  const [customerType, setCustomerType] = useState("GERCEK");
  const [formData, setFormData] = useState({
    nationality: "TC",
    country: "TURKIYE",
  });
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validate = () => {
    const newErrors = {};

    if (customerType === "GERCEK") {
      if (!/^\d{11}$/.test(formData.tcNumber || "")) {
        newErrors.tcNumber = "TC Number must be 11 digits.";
      }
      if (!/^[a-zA-ZÇçĞğİıÖöŞşÜü]+$/.test(formData.firstName || "")) {
        newErrors.firstName = "First name must contain only letters.";
      }
      if (!/^[a-zA-ZÇçĞğİıÖöŞşÜü]+$/.test(formData.lastName || "")) {
        newErrors.lastName = "Last name must contain only letters.";
      }
      if (!/(^ERKEK$|^KADIN$)/.test((formData.gender || "").toUpperCase())) {
        newErrors.gender = "Gender is required.";
      }
      if (!/^\d{4}-\d{2}-\d{2}$/.test(formData.dateOfBirth || "")) {
        newErrors.dateOfBirth = "Date of birth is required (YYYY-MM-DD).";
      }
    }

    if (customerType === "TUZEL") {
      if (!/^\d+$/.test(formData.taxNumber || "")) {
        newErrors.taxNumber = "Tax number must contain only digits.";
      }
      if (!formData.companyName) {
        newErrors.companyName = "Company name is required.";
      }
      if (!(formData.companyType || "").trim()) {
        newErrors.companyType = "Company type is required.";
      }
      if (!(formData.businessSector || "").trim()) {
        newErrors.businessSector = "Business sector is required.";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validate()) {
      try {
        const nationalityValue = (formData.nationality ?? "TC").toString().toUpperCase();
        const countryValue = (formData.country ?? "TURKIYE").toString().toUpperCase();
        const rawPayload = {
          mukellefTuru: customerType,
          ad: formData.firstName || "",
          soyad: formData.lastName || "",
          cinsiyet: (formData.gender || "").toUpperCase() || undefined,
          anaAd: formData.motherName || "",
          babaAd: formData.fatherName || "",
          dogumTarihi: formData.dateOfBirth || "",
          dogumYeri: formData.placeOfBirth || "",
          meslek: (formData.job || "").toUpperCase() || undefined,
          egitimDurumu: (formData.educationStatus || "").toUpperCase() || undefined,
          uyruk: nationalityValue,
          ulke: countryValue,
          tcNo: (formData.tcNumber ?? "").toString(),
          ozelMusteriMi: (formData.specialCustomer || "No") === "Yes",
          sgkKullaniyorMu: (formData.usesSGK || "No") === "Yes",
          ...(customerType === "TUZEL"
            ? {
                vergiNo: formData.taxNumber || "",
                sirketUnvani: formData.companyName || "",
                sirketTuru: (formData.companyType || "").toUpperCase() || undefined,
                sektor: (formData.businessSector || "").toUpperCase() || undefined,
              }
            : {}),
        };
        const payload = Object.fromEntries(
          Object.entries(rawPayload).filter(([_, v]) => v !== "" && v !== undefined && v !== null)
        );
        console.log("Submitting payload", payload);
        const response = await fetch("/api/musteri", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify(payload),
        });

        if (!response.ok) {
          const raw = await response.text().catch(() => "");
          let parsed;
          try { parsed = raw ? JSON.parse(raw) : undefined; } catch (_) {}
          const backendMessage = parsed?.message || parsed?.error || raw || "";
          console.error("Save customer failed", {
            status: response.status,
            statusText: response.statusText,
            backendMessage,
            payload,
          });
          throw new Error(
            `Failed to save customer (HTTP ${response.status}${response.statusText ? " " + response.statusText : ""})${backendMessage ? ` - ${backendMessage}` : ""}`
          );
        }
  
        const data = await response.json();
        const customerId = data.id ?? data.musteriId ?? data.musteri?.id ?? "N/A";
        alert(`Customer saved successfully! Code: ${customerId}`);
        console.log("Backend response:", data);
        // Clear the form fields after successful save
        setFormData({});
        setErrors({});
  
        // Optionally redirect to Contact/Address pages
        // navigate("/contact-info");
      } catch (error) {
        console.error(error);
        alert(`Error saving customer: ${error.message}`);
      }
    }
  };
  

  return (
    <form onSubmit={handleSubmit} className="general-info-form">
      {/* Customer Type Selection */}
      <div className="mb-4">
        <label className="form-label d-block">Customer Type</label>
        <div className="form-check form-check-inline">
          <input
            className="form-check-input"
            type="radio"
            name="customerType"
            value="GERCEK"
            id="customerTypeGercek"
            checked={customerType === "GERCEK"}
            onChange={() => setCustomerType("GERCEK")}
          />
          <label className="form-check-label" htmlFor="customerTypeGercek">Gerçek</label>
        </div>
        <div className="form-check form-check-inline">
          <input
            className="form-check-input"
            type="radio"
            name="customerType"
            value="TUZEL"
            id="customerTypeTuzel"
            checked={customerType === "TUZEL"}
            onChange={() => setCustomerType("TUZEL")}
          />
          <label className="form-check-label" htmlFor="customerTypeTuzel">Tüzel</label>
        </div>
      </div>

      {/* Conditional fields */}
      {customerType === "GERCEK" && (
        <IndividualFields formData={formData} onChange={handleChange} errors={errors} />
      )}
      {customerType === "TUZEL" && (
        <CorporateFields formData={formData} onChange={handleChange} errors={errors} />
      )}
      <CommonFields formData={formData} onChange={handleChange} errors={errors} />

      <div className="d-grid mt-3">
        <button type="submit" className="btn btn-primary">Save</button>
      </div>
    </form>
  );
};

export default GeneralInformationForm;
