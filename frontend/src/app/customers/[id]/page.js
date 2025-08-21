"use client";

import React, { useEffect, useMemo, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import AddressSection from "../../components/AddressSection";
import EmailSection from "../../components/EmailSection";
import PhoneSection from "../../components/PhoneSection";
import AccountInfoSection from "../../components/AccountInfoSection";

export default function CustomerDetailPage() {
	const params = useParams();
	const router = useRouter();
	const id = Array.isArray(params?.id) ? params.id[0] : params?.id;

	const [isLoading, setIsLoading] = useState(true);
	const [errorMessage, setErrorMessage] = useState("");
	const [data, setData] = useState(null);


	function getCustomerKind(record) {
		if (!record || typeof record !== "object") return null;
		const explicit = record.musteriTipi || record.type || record.tip;
		if (explicit && String(explicit).toUpperCase() === "GERCEK") return "GERCEK";
		if (explicit && String(explicit).toUpperCase() === "TUZEL") return "TUZEL";
		if (record.taxNumber || record.companyName || record.unvan || record.vergiNo) return "TUZEL";
		if (record.tcNo || record.tcNumber || record.firstName || record.lastName || record.adi || record.soyadi) return "GERCEK";
		return null;
	}

	function buildDisplayPairs(record) {
		const kind = getCustomerKind(record);
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

		return Object.entries(record || {});
	}

	useEffect(() => {
		async function run() {
			if (!id) return;
			setIsLoading(true);
			setErrorMessage("");
			try {
				const endpoint = /^(\d){11}$/.test(String(id))
					? `http://localhost:8080/api/musteri/tc/${encodeURIComponent(id)}`
					: `http://localhost:8080/api/musteri/id/${encodeURIComponent(id)}`;
				const res = await fetch(endpoint, { cache: "no-store" });
				if (!res.ok) throw new Error("Müşteri getirilemedi.");
				const json = await res.json();
				setData(json);
			} catch (err) {
				setErrorMessage(err?.message || "Bilinmeyen hata");
			} finally {
				setIsLoading(false);
			}
		}
		run();
	}, [id]);

	const musteriIdForAddress = useMemo(() => {
		if (data && data.id) return String(data.id);
		return id ? String(id) : "";
	}, [data, id]);

	useEffect(() => {
		// Address lifecycle moved into AddressSection
	}, [musteriIdForAddress]);

	return (
		<div style={{ maxWidth: 960, margin: "0 auto", padding: 24 }}>
			<button
				onClick={() => router.back()}
				style={{
					marginBottom: 16,
					background: "transparent",
					border: "1px solid #e5e7eb",
					borderRadius: 8,
					padding: "6px 10px",
					cursor: "pointer",
				}}
			>
				Geri
			</button>
			<h2 style={{ fontSize: 22, fontWeight: 700, marginBottom: 12 }}>Müşteri Detayı</h2>
			{isLoading ? <div>Yükleniyor...</div> : null}
			{errorMessage ? (
				<div style={{ color: "#b91c1c" }}>{errorMessage}</div>
			) : null}
			{!isLoading && !errorMessage && data ? (
				<div
					style={{
						border: "1px solid #e5e7eb",
						borderRadius: 10,
						padding: 16,
					}}
				>
					<div
						style={{
							display: "grid",
							gridTemplateColumns: "220px 1fr",
							gap: 10,
						}}
					>
						{buildDisplayPairs(data).map(([label, value], idx) => (
							<React.Fragment key={`${label}-${idx}`}>
								<div style={{ color: "#6b7280" }}>{label}</div>
								<div style={{ fontWeight: 500 }}>{String(value)}</div>
							</React.Fragment>
						))}
					</div>
				</div>
			) : null}

			{musteriIdForAddress ? (
				<>
					<AddressSection musteriId={musteriIdForAddress} />
					<EmailSection musteriId={musteriIdForAddress} />
					<PhoneSection musteriId={musteriIdForAddress} />
					<AccountInfoSection musteriId={musteriIdForAddress} />
				</>
			) : null}

			{false && musteriIdForAddress ? (
				<div style={{ marginTop: 24 }}>
					<h3 style={{ fontSize: 18, fontWeight: 700, marginBottom: 12 }}>Adres Ekle</h3>
					<form
						onSubmit={handleCreateAddress}
						style={{
							display: "grid",
							gridTemplateColumns: "1fr 1fr",
							gap: 12,
							border: "1px solid #e5e7eb",
							borderRadius: 10,
							padding: 16,
						}}
					>
						<div style={{ gridColumn: "1 / span 2", display: "flex", gap: 12, alignItems: "center" }}>
							<label style={{ minWidth: 180 }}>Yazışma adresi mi?</label>
							<div style={{ display: "flex", gap: 16 }}>
								<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
									<input
										type="radio"
										name="yazismaAdresiMi"
										checked={!!form.yazismaAdresiMi}
										onChange={() => setForm((p) => ({ ...p, yazismaAdresiMi: true }))}
									/>
									Evet
								</label>
								<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
									<input
										type="radio"
										name="yazismaAdresiMi"
										checked={!form.yazismaAdresiMi}
										onChange={() => setForm((p) => ({ ...p, yazismaAdresiMi: false }))}
									/>
									Hayır
								</label>
							</div>
						</div>

						<div>
							<label>Adres kısa adı</label>
							<input
								type="text"
								value={form.adresKisaAdi}
								onChange={(e) => setForm((p) => ({ ...p, adresKisaAdi: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							/>
						</div>

						<div>
							<label>Ülke</label>
							<select
								value={form.ulke}
								onChange={(e) => setForm((p) => ({ ...p, ulke: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							>
								<option value="TURKIYE">TÜRKİYE</option>
							</select>
						</div>

						<div>
							<label>İl</label>
							<select
								value={form.il}
								onChange={(e) => handleCityChange(e.target.value)}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							>
								{CITY_OPTIONS.map((c) => (
									<option key={c} value={c}>{c}</option>
								))}
							</select>
						</div>

						<div>
							<label>İlçe</label>
							<select
								value={form.ilce}
								onChange={(e) => setForm((p) => ({ ...p, ilce: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							>
								{(DISTRICTS_BY_CITY[form.il] || []).map((d) => (
									<option key={d} value={d}>{d}</option>
								))}
							</select>
						</div>

						<div>
							<label>Cadde</label>
							<input
								type="text"
								value={form.cadde}
								onChange={(e) => setForm((p) => ({ ...p, cadde: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							/>
						</div>

						<div>
							<label>Sokak</label>
							<input
								type="text"
								value={form.sokak}
								onChange={(e) => setForm((p) => ({ ...p, sokak: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							/>
						</div>

						<div>
							<label>Apartman adı</label>
							<input
								type="text"
								value={form.apartmanAdi}
								onChange={(e) => setForm((p) => ({ ...p, apartmanAdi: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							/>
						</div>

						<div>
							<label>Daire No</label>
							<input
								type="text"
								value={form.daireNo}
								onChange={(e) => setForm((p) => ({ ...p, daireNo: e.target.value }))}
								style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
							/>
						</div>

						<div style={{ gridColumn: "1 / span 2", display: "flex", justifyContent: "flex-end", gap: 8 }}>
							{createError ? (
								<div style={{ color: "#b91c1c", marginRight: "auto" }}>{createError}</div>
							) : null}
							<button
								type="submit"
								disabled={creating}
								style={{
									padding: "10px 16px",
									background: "#111827",
									color: "#fff",
									border: "none",
									borderRadius: 8,
									cursor: "pointer",
									opacity: creating ? 0.7 : 1,
								}}
							>
								{creating ? "Kaydediliyor..." : "Adresi Ekle"}
							</button>
						</div>
					</form>

					<div style={{ marginTop: 24 }}>
						<h3 style={{ fontSize: 18, fontWeight: 700, marginBottom: 8 }}>Adresler</h3>
						{addressesLoading ? <div>Yükleniyor...</div> : null}
						{addressesError ? (
							<div style={{ color: "#b91c1c" }}>{addressesError}</div>
						) : null}
						{!addressesLoading && !addressesError && addresses.length === 0 ? (
							<div>Kayıtlı adres yok.</div>
						) : null}
						{!addressesLoading && !addressesError && addresses.length > 0 ? (
							<div style={{ display: "grid", gap: 12 }}>
								{addresses.map((addr, idx) => {
									const isEditing = String(editingId) === String(addr?.id);
									return (
										<div key={addr?.id ?? idx} style={{ border: "1px solid #e5e7eb", borderRadius: 10, padding: 12 }}>
											{isEditing ? (
												<form onSubmit={handleUpdateAddress} style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
													<div style={{ gridColumn: "1 / span 2", display: "flex", gap: 12, alignItems: "center" }}>
														<label style={{ minWidth: 180 }}>Yazışma adresi mi?</label>
														<div style={{ display: "flex", gap: 16 }}>
															<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
																<input type="radio" name={`edit_yazisma_${addr?.id}`} checked={!!editForm?.yazismaAdresiMi} onChange={() => setEditForm((p) => ({ ...p, yazismaAdresiMi: true }))} />
																Evet
															</label>
															<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
																<input type="radio" name={`edit_yazisma_${addr?.id}`} checked={!editForm?.yazismaAdresiMi} onChange={() => setEditForm((p) => ({ ...p, yazismaAdresiMi: false }))} />
																Hayır
															</label>
														</div>
													</div>

													<div>
														<label>Adres kısa adı</label>
														<input type="text" value={editForm?.adresKisaAdi ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, adresKisaAdi: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
													</div>

													<div>
														<label>Ülke</label>
														<select value={editForm?.ulke ?? "TURKIYE"} onChange={(e) => setEditForm((p) => ({ ...p, ulke: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}>
															<option value="TURKIYE">TÜRKİYE</option>
														</select>
													</div>

													<div>
														<label>İl</label>
														<select value={editForm?.il ?? "ISTANBUL"} onChange={(e) => {
															const nextCity = e.target.value;
															const nextDistrict = (DISTRICTS_BY_CITY[nextCity] || [])[0] || "";
															setEditForm((p) => ({ ...p, il: nextCity, ilce: nextDistrict }));
														}} style={{ width: "100%,", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}>

															{CITY_OPTIONS.map((c) => (
																<option key={c} value={c}>{c}</option>
															))}
														</select>
													</div>

													<div>
														<label>İlçe</label>
														<select value={editForm?.ilce ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, ilce: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}>
															{(DISTRICTS_BY_CITY[editForm?.il || "ISTANBUL"] || []).map((d) => (
																<option key={d} value={d}>{d}</option>
															))}
														</select>
													</div>

													<div>
														<label>Cadde</label>
														<input type="text" value={editForm?.cadde ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, cadde: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
													</div>

													<div>
														<label>Sokak</label>
														<input type="text" value={editForm?.sokak ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, sokak: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
													</div>

													<div>
														<label>Apartman adı</label>
														<input type="text" value={editForm?.apartmanAdi ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, apartmanAdi: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
													</div>

													<div>
														<label>Daire No</label>
														<input type="text" value={editForm?.daireNo ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, daireNo: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
													</div>

													<div style={{ gridColumn: "1 / span 2", display: "flex", justifyContent: "flex-end", gap: 8, alignItems: "center" }}>
														{updateError ? <div style={{ color: "#b91c1c", marginRight: "auto" }}>{updateError}</div> : null}
														<button type="button" onClick={cancelEdit} style={{ padding: "8px 12px", background: "transparent", border: "1px solid #e5e7eb", borderRadius: 8, cursor: "pointer" }}>Vazgeç</button>
														<button type="submit" disabled={updating} style={{ padding: "10px 16px", background: "#111827", color: "#fff", border: "none", borderRadius: 8, cursor: "pointer", opacity: updating ? 0.7 : 1 }}>{updating ? "Güncelleniyor..." : "Kaydet"}</button>
													</div>
												</form>
											) : (
												<>
													<div style={{ display: "grid", gridTemplateColumns: "200px 1fr", gap: 8 }}>
														{Object.entries(addr).map(([k, v]) => (
															<React.Fragment key={k}>
																<div style={{ color: "#6b7280" }}>{k}</div>
																<div style={{ fontWeight: 500 }}>{typeof v === "object" && v !== null ? JSON.stringify(v) : String(v)}</div>
															</React.Fragment>
														))}
													</div>
													<div style={{ marginTop: 12, display: "flex", justifyContent: "flex-end", gap: 8, alignItems: "center" }}>
														{deleteError ? <div style={{ color: "#b91c1c", marginRight: "auto" }}>{deleteError}</div> : null}
														<button type="button" onClick={() => beginEdit(addr)} style={{ padding: "8px 12px", background: "transparent", border: "1px solid #e5e7eb", borderRadius: 8, cursor: "pointer" }}>Düzenle</button>
														<button type="button" onClick={() => handleDeleteAddress(addr?.id)} disabled={String(deletingId) === String(addr?.id)} style={{ padding: "8px 12px", background: "#b91c1c", color: "#fff", border: "none", borderRadius: 8, cursor: "pointer", opacity: String(deletingId) === String(addr?.id) ? 0.7 : 1 }}>{String(deletingId) === String(addr?.id) ? "Siliniyor..." : "Sil"}</button>
													</div>
												</>
											)}
										</div>
									);
								})}
							</div>
						) : null}
					</div>
				</div>
			) : null}
		</div>
	);
}


