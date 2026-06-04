// auth.manager.ts
import { authStorage } from "./auth.storage";

let isRefreshing = false;
let queue: ((token: string | null) => void)[] = [];

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

    authStorage.set(newToken);

    queue.forEach((cb) => cb(newToken));
    queue = [];

    return newToken;
  } catch (e) {
    queue.forEach((cb) => cb(null));
    queue = [];

    authStorage.clear();
    window.dispatchEvent(new Event("auth:logout"));

    return null;
  } finally {
    isRefreshing = false;
  }
}
