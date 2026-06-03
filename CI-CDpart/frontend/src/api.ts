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
    throw new Error("API Error");
  }

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
