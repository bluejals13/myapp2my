// auth.manager.ts refresh + queue
import { authStorage } from "./auth.storage";	// jwt 토큰 키 get, set, clear

let isRefreshing = false;
// Promise<string>
type RefreshResolver = (token: string | null) => void;

let queue: RefreshResolver[] = [];

export async function refreshToken(): Promise<string | null> {
  if (isRefreshing) {
    return new Promise((resolve) => {
      queue.push(resolve);
    });
  }

  isRefreshing = true;

  try {
    const res = await fetch("/api/auth/refresh", {
      method: "POST",
      credentials: "include",
    });

    if (!res.ok) throw new Error("refresh failed");

    const data = await res.json();
    const newToken = data.accessToken;

    const newToken =  data?.accessToken || data?.data?.accessToken; // ✔️ 추가 (중요)

    queue.forEach((cb) => cb(newToken));
    queue.length = 0;

    return newToken;
  } catch (e) {
    authStorage.clear(); // ✔️ 안전 처리
    
    queue.forEach((cb) => cb(null));
    queue.length = 0;

    window.dispatchEvent(new Event("auth:logout"));

    return null;
  } finally {
    isRefreshing = false;
  }
}
