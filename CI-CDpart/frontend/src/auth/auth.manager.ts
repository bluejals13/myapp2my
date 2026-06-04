// auth.manager.ts
import { authStorage } from "./auth.storage";

let isRefreshing = false;
let queue: any[] = [];

export async function refreshToken() {
  if (isRefreshing) return new Promise(resolve => queue.push(resolve));

  isRefreshing = true;

  try {
    const res = await fetch("/api/auth/refresh", {
      method: "POST",
      credentials: "include",
    });

    const data = await res.json();

    authStorage.set(data.accessToken);

    queue.forEach(cb => cb(data.accessToken));
    queue = [];

    return data.accessToken;
  } finally {
    isRefreshing = false;
  }
}
