// auth.interceptor.tsx 401 retry
import { apiFetch } from "../api";
import { authStorage } from "./auth.storage";	// jwt 토큰 키 get, set, clear
import { refreshToken } from "./auth.manager";

export async function apiWithAuth<T>(
  url: string,
  options: RequestInit = {}
): Promise<T> {
  try {
    return await apiFetch<T>(url, options);
  } catch (err: any) {
    const status = err?.status;

    if (status !== 401) {
      throw err;
    }

    const newToken = await refreshToken();

    if (!newToken) {
      throw err;
    }

    authStorage.set(newToken);

  // retry 1번만  
  const headers = new Headers(options.headers);

  headers.delete("Authorization");

  headers.set("Authorization", `Bearer ${newToken}`);

  return await apiFetch<T>(url, {
    ...options,
    headers,
    _retry: true,
    } as any);
  }
}
