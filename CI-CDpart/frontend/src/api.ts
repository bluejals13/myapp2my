// api.ts HTTP만
import { authStorage } from "./auth.storage";

export async function apiFetch<T>(
  url: string,
  options: RequestInit = {}
): Promise<T> {
  const token = authStorage.get();

  const headers = new Headers(options.headers);

  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  if (
    !(options.body instanceof FormData) &&
    !headers.has("Content-Type")
  ) {
    headers.set("Content-Type", "application/json");
  }

  const res = await fetch(url, {
    ...options,
    headers,
  });

  // body 없을 수도 있음 (204 대응)
  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    window.dispatchEvent(
      new CustomEvent("auth:error", {
        detail: {
          status: res.status,
          ...data,
        },
      })
    );

    throw {
      status: res.status,
      code: data?.code,
      message: data?.message,
    };
  }

  return data as T;
}
