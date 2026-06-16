// auth.manager.ts refresh + queue
import { authStorage } from "./auth.storage";	// jwt 토큰 키 get, set, clear

let refreshPromise: Promise<string | null> | null = null;

export async function refreshToken(): Promise<string | null> {
  if (refreshPromise) {
    return refreshPromise;
  }

  refreshPromise = (async () => {
    
    try {
      const res = await fetch("/api/auth/refresh", {
        method: "POST",
        credentials: "include",
      });

      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || "refresh failed");
      }

      const data = await res.json();

      const newToken =
        data?.accessToken || data?.data?.accessToken || null;

      if (!newToken) throw new Error("no token");

      authStorage.set(newToken);

      return newToken;

    } catch (e) {
      authStorage.clear();
      window.dispatchEvent(new Event("auth:logout"));
      return null;

    } finally {
      refreshPromise = null;
    }
    
  })();

  return refreshPromise;
}
