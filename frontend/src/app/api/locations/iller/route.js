import { NextResponse } from "next/server";

const BACKEND_BASE = "http://localhost:8080";

export async function GET() {
  const res = await fetch(`${BACKEND_BASE}/api/locations/iller`, { cache: "no-store" });
  const text = await res.text();
  return new NextResponse(text, { status: res.status, headers: { "content-type": res.headers.get("content-type") || "application/json" } });
}
