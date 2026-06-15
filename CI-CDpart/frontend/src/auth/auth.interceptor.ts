// auth.interceptor.tsx 401 retry
import { apiFetch } from "../api";
import { authStorage } from "./auth.storage";	// jwt 토큰 키 get, set, clear
import { refreshToken } from "./auth.manager";

let refreshPromise: Promise<string | null> | null = null;

export async function apiWithAuth<T>(
  url: string,
  options: RequestInit = {}
): Promise<T> {
  try {
    return await apiFetch<T>(url, options);
  } catch (err: any) { 
    
    const status = err?.status;                  
    if (status !== 401) { throw err; }                  
    
    const original = options as any;
    if (original._retry) throw err;
      original._retry = true;
                      
    try {
      if (!refreshPromise) {
        refreshPromise = refreshToken().finally(() => {
          refreshPromise = null;
        });
      }

    const newToken = await refreshPromise;
      
    if (!newToken) { throw err; }

    authStorage.set(newToken);

    const headers = new Headers(options.headers ?? {});
    headers.set("Authorization", `Bearer ${newToken}`);

    return apiFetch<T>(url, {
      ...options,
      headers,
    });
  } catch (e) {
      throw e;
    }   
  }
}
