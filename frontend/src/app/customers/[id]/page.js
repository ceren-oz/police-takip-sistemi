"use client";

import React, { useEffect, useMemo, useState } from "react";
import { useParams, useRouter } from "next/navigation";

export default function CustomerDetailPage() {
	const params = useParams();
	const router = useRouter();
	const id = Array.isArray(params?.id) ? params.id[0] : params?.id;

	const [isLoading, setIsLoading] = useState(true);
	const [errorMessage, setErrorMessage] = useState("");
	const [data, setData] = useState(null);

	const [addresses, setAddresses] = useState([]);
	const [addressesLoading, setAddressesLoading] = useState(false);
	const [addressesError, setAddressesError] = useState("");

	const [creating, setCreating] = useState(false);
	const [createError, setCreateError] = useState("");

	const [editingId, setEditingId] = useState(null);
	const [editForm, setEditForm] = useState(null);
	const [updating, setUpdating] = useState(false);
	const [updateError, setUpdateError] = useState("");
	const [deletingId, setDeletingId] = useState(null);
	const [deleteError, setDeleteError] = useState("");

	const CITY_OPTIONS = ["ISTANBUL"];
	const DISTRICTS_BY_CITY = {
		ISTANBUL: ["USKUDAR"],
	};

	const [form, setForm] = useState({
		yazismaAdresiMi: false,
		adresKisaAdi: "",
		ulke: "TURKIYE",
		il: "ISTANBUL",
		ilce: "USKUDAR",
		cadde: "",
		sokak: "",
		apartmanAdi: "",
		daireNo: "",
	});

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
				["First Name", ["firstName", "adi"]],
				["Last Name", ["lastName", "soyadi"]],
				["Gender", ["gender", "cinsiyet"]],
				["Mother's Name", ["motherName", "anneAdi"]],
				["Father's Name", ["fatherName", "babaAdi"]],
				["Date of Birth", ["dateOfBirth", "dogumTarihi"]],
				["Place of Birth", ["placeOfBirth", "dogumYeri"]],
				["Job", ["job", "meslek"]],
				["Education Status", ["educationStatus", "egitimDurumu"]],
				["Nationality", ["nationality", "uyruk"]],
				["Country", ["country", "ulke"]],
				["Uses SGK?", ["usesSGK", "sgk", "usesSgk"]],
				["Is Special Customer?", ["specialCustomer", "ozelMusteri"]],
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
				["Şirket Adı", ["companyName", "unvan", "company"]],
				["Şirket Türü", ["companyType", "sirketTuru"]],
				["Sektör", ["businessSector", "sektor"]],
				["Nationality", ["nationality", "uyruk"]],
				["Country", ["country", "ulke"]],
				["Uses SGK?", ["usesSGK", "sgk", "usesSgk"]],
				["Is Special Customer?", ["specialCustomer", "ozelMusteri"]],
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
		async function loadAddresses() {
			if (!musteriIdForAddress) return;
			setAddressesLoading(true);
			setAddressesError("");
			try {
				const url = `/api/musteri/${encodeURIComponent(
					musteriIdForAddress
				)}/adres`;
				const res = await fetch(url, { cache: "no-store" });
				if (res.status === 204) {
					setAddresses([]);
					return;
				}
				if (!res.ok) throw new Error("Adresler getirilemedi.");
				const list = await res.json();
				setAddresses(Array.isArray(list) ? list : []);
			} catch (err) {
				setAddressesError(err?.message || "Adres yükleme hatası");
			} finally {
				setAddressesLoading(false);
			}
		}
		loadAddresses();
	}, [musteriIdForAddress]);

	function handleCityChange(nextCity) {
		const districts = DISTRICTS_BY_CITY[nextCity] || [];
		setForm((prev) => ({ ...prev, il: nextCity, ilce: districts[0] || "" }));
	}

	async function handleCreateAddress(e) {
		e?.preventDefault?.();
		if (!musteriIdForAddress) return;
		setCreateError("");
		setCreating(true);
		try {
			const url = `/api/musteri/${encodeURIComponent(
				musteriIdForAddress
			)}/adres`;
			const res = await fetch(url, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(form),
			});
			if (!res.ok) throw new Error("Adres eklenemedi.");
			const created = await res.json();
			setAddresses((prev) => [created, ...prev]);
			setForm({
				yazismaAdresiMi: false,
				adresKisaAdi: "",
				ulke: "TURKIYE",
				il: "ISTANBUL",
				ilce: "USKUDAR",
				cadde: "",
				sokak: "",
				apartmanAdi: "",
				daireNo: "",
			});
		} catch (err) {
			setCreateError(err?.message || "Kayıt sırasında hata oluştu");
		} finally {
			setCreating(false);
		}
	}

	function beginEdit(addr) {
		const initial = {
			yazismaAdresiMi: !!addr.yazismaAdresiMi,
			adresKisaAdi: addr.adresKisaAdi ?? "",
			ulke: addr.ulke ?? "TURKIYE",
			il: addr.il ?? "ISTANBUL",
			ilce: addr.ilce ?? "USKUDAR",
			cadde: addr.cadde ?? "",
			sokak: addr.sokak ?? "",
			apartmanAdi: addr.apartmanAdi ?? "",
			daireNo: addr.daireNo ?? "",
		};
		setEditingId(addr?.id ?? null);
		setEditForm(initial);
		setUpdateError("");
	}

	function cancelEdit() {
		setEditingId(null);
		setEditForm(null);
		setUpdateError("");
	}

	async function handleUpdateAddress(e) {
		e?.preventDefault?.();
		if (!musteriIdForAddress || !editingId || !editForm) return;
		setUpdating(true);
		setUpdateError("");
		try {
			const url = `/api/musteri/${encodeURIComponent(
				musteriIdForAddress
			)}/adres/${encodeURIComponent(editingId)}`;
			const res = await fetch(url, {
				method: "PUT",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(editForm),
			});
			if (!res.ok) throw new Error("Adres güncellenemedi.");
			const updated = await res.json();
			setAddresses((prev) =>
				prev.map((a) => (String(a?.id) === String(updated?.id) ? updated : a))
			);
			cancelEdit();
		} catch (err) {
			setUpdateError(err?.message || "Güncelleme sırasında hata oluştu");
		} finally {
			setUpdating(false);
		}
	}

	async function handleDeleteAddress(addrId) {
		if (!musteriIdForAddress || !addrId) return;
		setDeleteError("");
		setDeletingId(addrId);
		try {
			const url = `/api/musteri/${encodeURIComponent(
				musteriIdForAddress
			)}/adres/${encodeURIComponent(addrId)}`;
			const res = await fetch(url, { method: "DELETE" });
			if (!res.ok && res.status !== 204) throw new Error("Adres silinemedi.");
			setAddresses((prev) => prev.filter((a) => String(a?.id) !== String(addrId)));
			if (String(editingId) === String(addrId)) cancelEdit();
		} catch (err) {
			setDeleteError(err?.message || "Silme sırasında hata oluştu");
		} finally {
			setDeletingId(null);
		}
	}

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


