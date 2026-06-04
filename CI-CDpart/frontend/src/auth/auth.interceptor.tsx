import { authStorage } from "./auth.storage";
import { refreshToken } from "./auth.manager";

export async function apiFetch(url, options = {}) {
  const token = authStorage.get();

  const res = await fetch(url, {
    ...options,
    headers: {
      ...options.headers,
      Authorization: token ? `Bearer ${token}` : "",
    },
  });

  if (res.status === 401) {
    const data = await res.json().catch(() => null);

    if (data?.code === "TOKEN_EXPIRED") {
      const newToken = await refreshToken();
      authStorage.set(newToken);

      return apiFetch(url, options); // retry
    }

    if (data?.code === "SESSION_INVALID") {
      authStorage.clear();
      window.dispatchEvent(new Event("auth:logout"));
    }
  }

  return res.json();
}
