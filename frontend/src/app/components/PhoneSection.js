"use client";

import React, { useEffect, useState } from "react";

export default function PhoneSection({ musteriId }) {
	const [items, setItems] = useState([]);
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState("");

	const [creating, setCreating] = useState(false);
	const [createError, setCreateError] = useState("");

	const [editingId, setEditingId] = useState(null);
	const [editForm, setEditForm] = useState(null);
	const [updating, setUpdating] = useState(false);
	const [updateError, setUpdateError] = useState("");
	const [deletingId, setDeletingId] = useState(null);
	const [deleteError, setDeleteError] = useState("");

	const PHONE_TYPES = ["CEP", "EV", "IS", "DIGER"];

	const [form, setForm] = useState({
		iletisimTelefonuMu: false,
		telefonTipi: "CEP",
		ulkeKodu: "",
		alanKodu: "",
		telefonNumarasi: "",
	});

	async function load() {
		if (!musteriId) return;
		setLoading(true);
		setError("");
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/telefonlar`;
			const res = await fetch(url, { cache: "no-store" });
			if (res.status === 204) {
				setItems([]);
				return;
			}
			if (!res.ok) throw new Error("Telefonlar getirilemedi.");
			const list = await res.json();
			setItems(Array.isArray(list) ? list : []);
		} catch (err) {
			setError(err?.message || "Telefon yükleme hatası");
		} finally {
			setLoading(false);
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
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/telefonlar`;

            			// 🐛 Debug: Log what we're sending
			console.log("Sending to backend:", form);

			const res = await fetch(url, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(form),
			});
			if (!res.ok) throw new Error("Telefon eklenemedi.");
			const created = await res.json();

            // 🐛 Debug: Log what we got back
			console.log("Received from backend:", created);

			setItems((prev) => [created, ...prev]);
			setForm({ iletisimTelefonuMu: false, telefonTipi: "CEP", ulkeKodu: "", alanKodu: "", telefonNumarasi: "" });
		} catch (err) {
			setCreateError(err?.message || "Kayıt sırasında hata oluştu");
		} finally {
			setCreating(false);
		}
	}

	function beginEdit(item) {
		setEditingId(item?.id ?? null);
		setEditForm({
			iletisimTelefonuMu: !!item?.iletisimTelefonuMu,
			telefonTipi: item?.telefonTipi ?? "CEP",
			ulkeKodu: item?.ulkeKodu ?? "",
			alanKodu: item?.alanKodu ?? "",
			telefonNumarasi: item?.telefonNumarasi ?? "",
		});
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
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/telefonlar/${encodeURIComponent(editingId)}`;
			const res = await fetch(url, {
				method: "PUT",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(editForm),
			});
			if (!res.ok) throw new Error("Telefon güncellenemedi.");
			const updated = await res.json();
			setItems((prev) => prev.map((a) => (String(a?.id) === String(updated?.id) ? updated : a)));
			cancelEdit();
		} catch (err) {
			setUpdateError(err?.message || "Güncelleme sırasında hata oluştu");
		} finally {
			setUpdating(false);
		}
	}

	async function handleDelete(itemId) {
		if (!musteriId || !itemId) return;
		setDeleteError("");
		setDeletingId(itemId);
		try {
			const url = `/api/musteri/${encodeURIComponent(musteriId)}/telefonlar/${encodeURIComponent(itemId)}`;
			const res = await fetch(url, { method: "DELETE" });
			if (!res.ok && res.status !== 204) throw new Error("Telefon silinemedi.");
			setItems((prev) => prev.filter((a) => String(a?.id) !== String(itemId)));
			if (String(editingId) === String(itemId)) cancelEdit();
		} catch (err) {
			setDeleteError(err?.message || "Silme sırasında hata oluştu");
		} finally {
			setDeletingId(null);
		}
	}

	return (
		<div style={{ marginTop: 24 }}>
			<h3 style={{ fontSize: 18, fontWeight: 700, marginBottom: 12 }}>Telefon Ekle</h3>
			<form onSubmit={handleCreate} style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12, border: "1px solid #e5e7eb", borderRadius: 10, padding: 16 }}>
				<div style={{ gridColumn: "1 / span 2", display: "flex", gap: 12, alignItems: "center" }}>
					<label style={{ minWidth: 240 }}>İletişim Telefonu Mu?</label>
					<div style={{ display: "flex", gap: 16 }}>
						<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
							<input 
								type="radio" 
								name="iletisimTelefonuMu" 
								checked={!!form.iletisimTelefonuMu} 
								onChange={() => setForm((p) => ({ ...p, iletisimTelefonuMu: true }))} 
							/>
							Evet
						</label>
						<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
							<input 
								type="radio" 
								name="iletisimTelefonuMu" 
								checked={!form.iletisimTelefonuMu} 
								onChange={() => setForm((p) => ({ ...p, iletisimTelefonuMu: false }))} 
							/>
							Hayır
						</label>
					</div>
				</div>

				<div>
					<label>Telefon Tipi</label>
					<select 
						value={form.telefonTipi} 
						onChange={(e) => setForm((p) => ({ ...p, telefonTipi: e.target.value }))} 
						style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
					>
						{PHONE_TYPES.map((t) => (
							<option key={t} value={t}>{t}</option>
						))}
					</select>
				</div>

				<div>
					<label>Ülke Kodu</label>
					<input 
						type="text" 
						value={form.ulkeKodu} 
						onChange={(e) => setForm((p) => ({ ...p, ulkeKodu: e.target.value }))} 
						style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
					/>
				</div>

				<div>
					<label>Alan Kodu</label>
					<input 
						type="text" 
						value={form.alanKodu} 
						onChange={(e) => setForm((p) => ({ ...p, alanKodu: e.target.value }))} 
						style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
					/>
				</div>

				<div>
					<label>Telefon No</label>
					<input 
						type="text" 
						value={form.telefonNumarasi} 
						onChange={(e) => setForm((p) => ({ ...p, telefonNumarasi: e.target.value }))} 
						style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
					/>
				</div>

				<div style={{ gridColumn: "1 / span 2", display: "flex", justifyContent: "flex-end", gap: 8 }}>
					{createError && <div style={{ color: "#b91c1c", marginRight: "auto" }}>{createError}</div>}
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
							opacity: creating ? 0.7 : 1 
						}}
					>
						{creating ? "Kaydediliyor..." : "Telefonu Ekle"}
					</button>
				</div>
			</form>

			<div style={{ marginTop: 24 }}>
				<h3 style={{ fontSize: 18, fontWeight: 700, marginBottom: 8 }}>Telefonlar</h3>
				{loading && <div>Yükleniyor...</div>}
				{error && <div style={{ color: "#b91c1c" }}>{error}</div>}
				{!loading && !error && items.length === 0 && <div>Kayıtlı telefon yok.</div>}
				{!loading && !error && items.length > 0 && (
					<div style={{ display: "grid", gap: 12 }}>
						{items.map((item, idx) => {
							const isEditing = String(editingId) === String(item?.id);
							return (
								<div key={item?.id ?? idx} style={{ border: "1px solid #e5e7eb", borderRadius: 10, padding: 12 }}>
									{isEditing ? (
										<form onSubmit={handleUpdate} style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
											<div style={{ gridColumn: "1 / span 2", display: "flex", gap: 12, alignItems: "center" }}>
												<label style={{ minWidth: 240 }}>İletişim Telefonu Mu?</label>
												<div style={{ display: "flex", gap: 16 }}>
													<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
														<input 
															type="radio" 
															name={`edit_iletisim_${item?.id}`} 
															checked={!!editForm?.iletisimTelefonuMu} 
															onChange={() => setEditForm((p) => ({ ...p, iletisimTelefonuMu: true }))} 
														/>
														Evet
													</label>
													<label style={{ display: "flex", gap: 6, alignItems: "center" }}>
														<input 
															type="radio" 
															name={`edit_iletisim_${item?.id}`} 
															checked={!editForm?.iletisimTelefonuMu} 
															onChange={() => setEditForm((p) => ({ ...p, iletisimTelefonuMu: false }))} 
														/>
														Hayır
													</label>
												</div>
											</div>

											<div>
												<label>Telefon Tipi</label>
												<select 
													value={editForm?.telefonTipi ?? "CEP"} 
													onChange={(e) => setEditForm((p) => ({ ...p, telefonTipi: e.target.value }))} 
													style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }}
												>
													{PHONE_TYPES.map((t) => (
														<option key={t} value={t}>{t}</option>
													))}
												</select>
											</div>

											<div>
												<label>Ülke Kodu</label>
												<input 
													type="text" 
													value={editForm?.ulkeKodu ?? ""} 
													onChange={(e) => setEditForm((p) => ({ ...p, ulkeKodu: e.target.value }))} 
													style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
												/>
											</div>

											<div>
												<label>Alan Kodu</label>
												<input 
													type="text" 
													value={editForm?.alanKodu ?? ""} 
													onChange={(e) => setEditForm((p) => ({ ...p, alanKodu: e.target.value }))} 
													style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
												/>
											</div>

											<div>
												<label>Telefon No</label>
												<input 
													type="text" 
													value={editForm?.telefonNumarasi ?? ""} 
													onChange={(e) => setEditForm((p) => ({ ...p, telefonNumarasi: e.target.value }))} 
													style={{ width: "100%", padding: "8px 10px", border: "1px solid #d1d5db", borderRadius: 8 }} 
												/>
											</div>

											<div style={{ gridColumn: "1 / span 2", display: "flex", justifyContent: "flex-end", gap: 8, alignItems: "center" }}>
												{updateError && <div style={{ color: "#b91c1c", marginRight: "auto" }}>{updateError}</div>}
												<button 
													type="button" 
													onClick={cancelEdit} 
													style={{ 
														padding: "8px 12px", 
														background: "transparent", 
														border: "1px solid #e5e7eb", 
														borderRadius: 8, 
														cursor: "pointer" 
													}}
												>
													Vazgeç
												</button>
												<button 
													type="submit" 
													disabled={updating} 
													style={{ 
														padding: "10px 16px", 
														background: "#111827", 
														color: "#fff", 
														border: "none", 
														borderRadius: 8, 
														cursor: "pointer", 
														opacity: updating ? 0.7 : 1 
													}}
												>
													{updating ? "Güncelleniyor..." : "Kaydet"}
												</button>
											</div>
										</form>
									) : (
										<>
											<div style={{ display: "grid", gridTemplateColumns: "200px 1fr", gap: 8 }}>
												{Object.entries(item)
													.filter(([k]) => !["id", "musteriId", "customerId"].includes(k))
													.map(([k, v]) => (
														<React.Fragment key={k}>
															<div style={{ color: "#6b7280" }}>{k}</div>
															<div style={{ fontWeight: 500 }}>
																{typeof v === "object" && v !== null ? JSON.stringify(v) : String(v)}
															</div>
														</React.Fragment>
													))}
											</div>
											<div style={{ marginTop: 12, display: "flex", justifyContent: "flex-end", gap: 8, alignItems: "center" }}>
												{deleteError && <div style={{ color: "#b91c1c", marginRight: "auto" }}>{deleteError}</div>}
												<button 
													type="button" 
													onClick={() => beginEdit(item)} 
													style={{ 
														padding: "8px 12px", 
														background: "transparent", 
														border: "1px solid #e5e7eb", 
														borderRadius: 8, 
														cursor: "pointer" 
													}}
												>
													Düzenle
												</button>
												<button 
													type="button" 
													onClick={() => handleDelete(item?.id)} 
													disabled={String(deletingId) === String(item?.id)} 
													style={{ 
														padding: "8px 12px", 
														background: "#b91c1c", 
														color: "#fff", 
														border: "none", 
														borderRadius: 8, 
														cursor: "pointer", 
														opacity: String(deletingId) === String(item?.id) ? 0.7 : 1 
													}}
												>
													{String(deletingId) === String(item?.id) ? "Siliniyor..." : "Sil"}
												</button>
											</div>
										</>
									)}
								</div>
							);
						})}
					</div>
				)}
			</div>
		</div>
	);
}