"use client";

import React, { useEffect, useState } from "react";
import CityDistrictSelector from "./CityDistrictSelector";

export default function AddressSection({ musteriId }) {
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

	const [form, setForm] = useState({
		yazismaAdresiMi: false,
		adresKisaAd: "",
		ulke: "TURKIYE",
		il: "",
		ilce: "",
		cadde: "",
		sokak: "",
		apartmanAdi: "",
		daireNo: "",
	});

	async function load() {
		if (!musteriId) return;
		setAddressesLoading(true);
		setAddressesError("");
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/adres`;
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

	useEffect(() => {
		load();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [musteriId]);

	async function handleCreate(e) {
		e?.preventDefault?.();
		if (!musteriId) return;
		setCreateError("");
		setCreating(true);
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/adres`;
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
				adresKisaAd: "",
				ulke: "TURKIYE",
				il: "",
				ilce: "",
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
			adresKisaAd: addr.adresKisaAd ?? "",
			ulke: addr.ulke ?? "TURKIYE",
			il: addr.il ?? "",
			ilce: addr.ilce ?? "",
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

	async function handleUpdate(e) {
		e?.preventDefault?.();
		if (!musteriId || !editingId || !editForm) return;
		setUpdating(true);
		setUpdateError("");
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/adres/${encodeURIComponent(editingId)}`;
			const res = await fetch(url, {
				method: "PUT",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(editForm),
			});
			if (!res.ok) throw new Error("Adres güncellenemedi.");
			const updated = await res.json();
			setAddresses((prev) => prev.map((a) => (String(a?.id) === String(updated?.id) ? updated : a)));
			cancelEdit();
		} catch (err) {
			setUpdateError(err?.message || "Güncelleme sırasında hata oluştu");
		} finally {
			setUpdating(false);
		}
	}

	async function handleDelete(addrId) {
		if (!musteriId || !addrId) return;
		setDeleteError("");
		setDeletingId(addrId);
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/adres/${encodeURIComponent(addrId)}`;
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
		<div style={{ marginTop: 24 }}>
			<h3 style={{ fontSize: 18, fontWeight: 700, marginBottom: 12 }}>Adres Ekle</h3>
			<form
				onSubmit={handleCreate}
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
						value={form.adresKisaAd}
						onChange={(e) => setForm((p) => ({ ...p, adresKisaAd: e.target.value }))}
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

				<div style={{ gridColumn: "1 / span 2" }}>
					<CityDistrictSelector
						city={form.il}
						district={form.ilce}
						onCityChange={(val) => setForm((p) => ({ ...p, il: val, ilce: "" }))}
						onDistrictChange={(val) => setForm((p) => ({ ...p, ilce: val }))}
					/>
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
										<form onSubmit={handleUpdate} style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
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
												<input type="text" value={editForm?.adresKisaAd ?? ""} onChange={(e) => setEditForm((p) => ({ ...p, adresKisaAd: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} />
											</div>

											<div>
												<label>Ülke</label>
												<select value={editForm?.ulke ?? "TURKIYE"} onChange={(e) => setEditForm((p) => ({ ...p, ulke: e.target.value }))} style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}>
													<option value="TURKIYE">TÜRKİYE</option>
												</select>
											</div>

											<div style={{ gridColumn: "1 / span 2" }}>
												<CityDistrictSelector
													city={editForm?.il ?? ""}
													district={editForm?.ilce ?? ""}
													onCityChange={(val) => setEditForm((p) => ({ ...p, il: val, ilce: "" }))}
													onDistrictChange={(val) => setEditForm((p) => ({ ...p, ilce: val }))}
												/>
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
												{Object.entries(addr)
													.filter(([k]) => !["id", "musteriId", "customerId"].includes(k))
													.map(([k, v]) => (
														<React.Fragment key={k}>
															<div style={{ color: "#6b7280" }}>{k}</div>
															<div style={{ fontWeight: 500 }}>{typeof v === "object" && v !== null ? JSON.stringify(v) : String(v)}</div>
														</React.Fragment>
													))}
											</div>
											<div style={{ marginTop: 12, display: "flex", justifyContent: "flex-end", gap: 8, alignItems: "center" }}>
												{deleteError ? <div style={{ color: "#b91c1c", marginRight: "auto" }}>{deleteError}</div> : null}
												<button type="button" onClick={() => beginEdit(addr)} style={{ padding: "8px 12px", background: "transparent", border: "1px solid #e5e7eb", borderRadius: 8, cursor: "pointer" }}>Düzenle</button>
												<button type="button" onClick={() => handleDelete(addr?.id)} disabled={String(deletingId) === String(addr?.id)} style={{ padding: "8px 12px", background: "#b91c1c", color: "#fff", border: "none", borderRadius: 8, cursor: "pointer", opacity: String(deletingId) === String(addr?.id) ? 0.7 : 1 }}>{String(deletingId) === String(addr?.id) ? "Siliniyor..." : "Sil"}</button>
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
	);
}


