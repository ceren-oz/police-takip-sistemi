"use client";
import React, { useEffect, useState } from "react";

const BACKEND_BASE = "http://localhost:8080";

export default function CityDistrictSelector({
  city,
  district,
  onCityChange,
  onDistrictChange,
}) {
  const [cities, setCities] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [loadingCities, setLoadingCities] = useState(false);
  const [loadingDistricts, setLoadingDistricts] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    (async () => {
      setLoadingCities(true);
      setError("");
      try {
        const res = await fetch(`${BACKEND_BASE}/api/locations/iller`, { cache: "no-store" });
        if (!res.ok) throw new Error("Şehirler yüklenemedi.");
        const list = await res.json();
        if (!cancelled) setCities(Array.isArray(list) ? list : []);
      } catch (e) {
        if (!cancelled) setError(e?.message || "Hata");
      } finally {
        if (!cancelled) setLoadingCities(false);
      }
    })();
    return () => { cancelled = true; };
  }, []);

  useEffect(() => {
    if (!city) { setDistricts([]); return; }
    let cancelled = false;
    (async () => {
      setLoadingDistricts(true);
      setError("");
      try {
        const res = await fetch(`${BACKEND_BASE}/api/locations/iller/${encodeURIComponent(city)}/ilceler`, { cache: "no-store" });
        if (!res.ok) throw new Error("İlçeler yüklenemedi.");
        const list = await res.json();
        if (!cancelled) {
          setDistricts(Array.isArray(list) ? list : []);
          // Reset district if not in new list
          if (!list?.includes(district)) onDistrictChange?.("");
        }
      } catch (e) {
        if (!cancelled) setError(e?.message || "Hata");
      } finally {
        if (!cancelled) setLoadingDistricts(false);
      }
    })();
    return () => { cancelled = true; };
  }, [city]); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
      <div>
        <label>İl</label>
        <select
          value={city || ""}
          onChange={(e) => onCityChange?.(e.target.value)}
          disabled={loadingCities}
          style={{ width: "100%", padding: 8, border: "1px solid #d1d5db", borderRadius: 8 }}
        >
          <option value="">{loadingCities ? "Yükleniyor..." : "Seçiniz"}</option>
          {cities.map((c) => (
            <option key={c} value={c}>{c}</option>
          ))}
        </select>
      </div>

      <div>
        <label>İlçe</label>
        <select
          value={district || ""}
          onChange={(e) => onDistrictChange?.(e.target.value)}
          disabled={!city || loadingDistricts}
          style={{ width: "100%", padding: 8, border: "1px solid #d1d5db", borderRadius: 8 }}
        >
          <option value="">{loadingDistricts ? "Yükleniyor..." : "Seçiniz"}</option>
          {districts.map((d) => (
            <option key={d} value={d}>{d}</option>
          ))}
        </select>
      </div>

      {error ? <div style={{ gridColumn: "1 / span 2", color: "#b91c1c" }}>{error}</div> : null}
    </div>
  );
}