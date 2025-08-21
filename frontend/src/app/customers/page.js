"use client";

import React, { useCallback, useMemo, useState } from "react";
import { useRouter } from "next/navigation";

function isLikelyTcNo(value) {
	return /^\d{11}$/.test((value || "").trim());
}

async function fetchCustomerByCode(customerCode) {
	const trimmed = (customerCode || "").trim();
	if (!trimmed) {
		throw new Error("Lütfen müşteri kodu giriniz.");
	}

	const endpoint = isLikelyTcNo(trimmed)
		? `http://localhost:8080/api/musteri/tc/${encodeURIComponent(trimmed)}`
		: `http://localhost:8080/api/musteri/id/${encodeURIComponent(trimmed)}`;

	const response = await fetch(endpoint, { cache: "no-store" });
	if (!response.ok) {
		throw new Error("Müşteri bulunamadı veya sunucu hatası oluştu.");
	}
	return response.json();
}

export default function CustomerSearchPage() {
	const router = useRouter();
	const [customerCode, setCustomerCode] = useState("");
	const [isLoading, setIsLoading] = useState(false);
	const [errorMessage, setErrorMessage] = useState("");
	const [result, setResult] = useState(null);

	function getCustomerKind(record) {
		if (!record || typeof record !== "object") return null;
		const explicit = record.musteriTipi || record.type || record.tip;
		if (explicit && String(explicit).toUpperCase() === "GERCEK") return "GERCEK";
		if (explicit && String(explicit).toUpperCase() === "TUZEL") return "TUZEL";
		if (record.taxNumber || record.companyName || record.unvan || record.vergiNo) return "TUZEL";
		if (record.tcNo || record.tcNumber || record.firstName || record.lastName || record.ad || record.soyad) return "GERCEK";
		return null;
	}

	function buildDisplayPairs(record) {
		const kind = getCustomerKind(record);
		if (!kind) return Object.entries(record || {});

		const getFirstPresent = (keys) => {
			for (const k of keys) {
				if (record[k] !== undefined && record[k] !== null && String(record[k]).trim() !== "") {
					return record[k];
				}
			}
			return undefined;
		};

		if (kind === "GERCEK") {
			const fields = [
				["TC Number", ["tcNumber", "tcNo"]],
				["First Name", ["firstName", "ad"]],
				["Last Name", ["lastName", "soyad"]],
				["Gender", ["gender", "cinsiyet"]],
				["Mother's Name", ["motherName", "anneAdi"]],
				["Father's Name", ["fatherName", "babaAdi"]],
				["Date of Birth", ["dateOfBirth", "dogumTarihi"]],
				["Place of Birth", ["placeOfBirth", "dogumYeri"]],
				["Job", ["job", "meslek"]],
				["Education Status", ["educationStatus", "egitimDurumu"]],
				// Common fields
				["Nationality", ["nationality", "uyruk"]],
				["Country", ["country", "ulke"]],
				["Uses SGK?", ["usesSGK", "sgk", "sgkKullaniyorMu"]],
				["Is Special Customer?", ["specialCustomer", "ozelMusteriMi"]],
			];
			return fields
				.map(([label, keys]) => {
					const v = getFirstPresent(keys);
					return v === undefined ? null : [label, v];
				})
				.filter(Boolean);
		}

		if (kind === "TUZEL") {
			const fields = [
				["Vergi No", ["taxNumber", "vergiNo"]],
				["Şirket Adı", ["companyName", "sirketUnvani", "company"]],
				["Şirket Türü", ["companyType", "sirketTuru"]],
				["Sektör", ["businessSector", "sektor"]],
				// Common fields
				["Nationality", ["nationality", "uyruk"]],
				["Country", ["country", "ulke"]],
				["Uses SGK?", ["usesSGK", "sgk", "sgkKullaniyorMu"]],
				["Is Special Customer?", ["specialCustomer", "ozelMusteriMi"]],
			];
			return fields
				.map(([label, keys]) => {
					const v = getFirstPresent(keys);
					return v === undefined ? null : [label, v];
				})
				.filter(Boolean);
		}

		return [];
	}

	const handleSearch = useCallback(async () => {
		setErrorMessage("");
		setIsLoading(true);
		setResult(null);
		try {
			const data = await fetchCustomerByCode(customerCode);
			setResult(data);
		} catch (err) {
			setErrorMessage(err?.message || "Arama sırasında bir hata oluştu.");
		} finally {
			setIsLoading(false);
		}
	}, [customerCode]);

	const primaryId = useMemo(() => {
		if (!result || typeof result !== "object") return "";
		if (result.id) return String(result.id);
		if (result.tcNo && isLikelyTcNo(String(result.tcNo))) return String(result.tcNo);
		return "";
	}, [result]);

	return (
		<div style={{ maxWidth: 960, margin: "0 auto", padding: 24 }}>
			<h2 style={{ fontSize: 22, fontWeight: 700, marginBottom: 16 }}>Müşteri Arama</h2>
			<div
				style={{
					display: "flex",
					gap: 8,
					alignItems: "center",
					marginBottom: 16,
				}}
			>
				<input
					type="text"
					value={customerCode}
					onChange={(e) => setCustomerCode(e.target.value)}
					placeholder="Müşteri Kodu veya T.C. No"
					style={{
						flex: 1,
						padding: "10px 12px",
						border: "1px solid #d1d5db",
						borderRadius: 8,
						outline: "none",
					}}
				/>
				<button
					onClick={handleSearch}
					disabled={isLoading}
					style={{
						padding: "10px 16px",
						background: "#111827",
						color: "#fff",
						border: "none",
						borderRadius: 8,
						cursor: "pointer",
						opacity: isLoading ? 0.7 : 1,
					}}
				>
					{isLoading ? "Aranıyor..." : "Ara"}
				</button>
			</div>
			{errorMessage ? (
				<div style={{ color: "#b91c1c", marginBottom: 12 }}>{errorMessage}</div>
			) : null}

			{result ? (
				<div>
					<div
						onClick={() => primaryId && router.push(`/customers/${encodeURIComponent(primaryId)}`)}
						role="button"
						tabIndex={0}
						style={{
							border: "1px solid #e5e7eb",
							borderRadius: 10,
							padding: 16,
							marginTop: 8,
							cursor: primaryId ? "pointer" : "default",
							boxShadow: "0 1px 2px rgba(0,0,0,0.04)",
						}}
					>
						<div style={{ fontWeight: 600, marginBottom: 8 }}>Arama Sonucu</div>
						<div
							style={{
								display: "grid",
								gridTemplateColumns: "200px 1fr",
								gap: 8,
							}}
						>
							{buildDisplayPairs(result).map(([label, value], idx) => (
								<React.Fragment key={`${label}-${idx}`}>
									<div style={{ color: "#6b7280" }}>{label}</div>
									<div style={{ fontWeight: 500 }}>{String(value)}</div>
								</React.Fragment>
							))}
						</div>
						{primaryId ? (
							<div style={{ marginTop: 12, color: "#2563eb" }}>Detayları görmek için tıklayınız</div>
						) : null}
					</div>
				</div>
			) : null}
		</div>
	);
}


