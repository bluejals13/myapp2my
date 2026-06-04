// api.ts
import { authStorage } from "./auth.storage";

export async function apiFetch<T>(url: string, options: RequestInit = {}) {
  const token = authStorage.get();

  const headers = new Headers(options.headers);

  if (token) headers.set("Authorization", `Bearer ${token}`);
  if (!headers.has("Content-Type") && !(options.body instanceof FormData)) {
    headers.set("Content-Type", "application/json");
  }

  const res = await fetch(url, { ...options, headers });

  const data = await res.json().catch(() => null);

  if (!res.ok) {    
      window.dispatchEvent(
        new CustomEvent("auth:error", { detail: { status: res.status, ...data } })
        );
      throw new Error("API Error");
    }

  console.log("METHOD =", options.method);
  console.log("BODY =", options.body);

  return data;
}
