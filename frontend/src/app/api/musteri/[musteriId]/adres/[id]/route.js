import { NextResponse } from "next/server";

const BACKEND_BASE = "http://localhost:8080";

export async function GET(_req, { params }) {
  const musteriId = Array.isArray(params?.musteriId) ? params.musteriId[0] : params?.musteriId;
  const id = Array.isArray(params?.id) ? params.id[0] : params?.id;
  const url = `${BACKEND_BASE}/api/musteri/${encodeURIComponent(musteriId)}/adres/${encodeURIComponent(id)}`;
  const res = await fetch(url, { cache: "no-store" });
  const text = await res.text();
  return new NextResponse(text, { status: res.status, headers: { "content-type": res.headers.get("content-type") || "application/json" } });
}

export async function PUT(req, { params }) {
  const musteriId = Array.isArray(params?.musteriId) ? params.musteriId[0] : params?.musteriId;
  const id = Array.isArray(params?.id) ? params.id[0] : params?.id;
  const body = await req.text();
  const url = `${BACKEND_BASE}/api/musteri/${encodeURIComponent(musteriId)}/adres/${encodeURIComponent(id)}`;
  const res = await fetch(url, {
    method: "PUT",
    headers: { "content-type": req.headers.get("content-type") || "application/json" },
    body,
  });
  const text = await res.text();
  return new NextResponse(text, { status: res.status, headers: { "content-type": res.headers.get("content-type") || "application/json" } });
}

export async function DELETE(_req, { params }) {
  const musteriId = Array.isArray(params?.musteriId) ? params.musteriId[0] : params?.musteriId;
  const id = Array.isArray(params?.id) ? params.id[0] : params?.id;
  const url = `${BACKEND_BASE}/api/musteri/${encodeURIComponent(musteriId)}/adres/${encodeURIComponent(id)}`;
  const res = await fetch(url, { method: "DELETE" });
  return new NextResponse(null, { status: res.status });
}


