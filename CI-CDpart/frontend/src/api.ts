import { z } from "zod";

export async function apiFetch<T>(
  url: string,
  schema?: z.ZodSchema<T>,
  options: RequestInit = {}
): Promise<T> {
  const token = localStorage.getItem("token");

  const headers = new Headers(options.headers);

  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  const isFormData = options.body instanceof FormData;

  if (!isFormData && !headers.has("Content-Type")) {
    headers.set("Content-Type", "application/json");
  }

  const res = await fetch(url, {
    ...options,
    headers,
  });

  if (!res.ok) {
  const errorBody = await res.json().catch(() => null);

  // 1. 세션 끊김 (다른 기기 로그인)
  if (res.status === 401 && errorBody?.code === "SESSION_INVALID") {
      authStorage.clear();
      window.dispatchEvent(new Event("auth:logout"));
      throw new Error("SESSION_INVALID");
      }

      // 2. 토큰 만료 → refresh 시도
  if (res.status === 401 && errorBody?.code === "TOKEN_EXPIRED") {
      const newToken = await refreshToken();

      authStorage.set(newToken);

      // retry logic (중요)
      return apiFetch(url, schema, options);
      }
      throw new Error("API Error");
    }

  console.log("METHOD =", options.method);
  console.log("BODY =", options.body);

  const data = await res.json();

  if (schema) {
  const result = schema.safeParse(data);

    if (!result.success) {
      console.error("Zod Error:", result.error.flatten());
      throw new Error("Response schema mismatch");
    }
    return result.data;
  }

  return data as T;
}
