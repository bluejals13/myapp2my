// api.ts

import { z } from "zod";

export async function apiFetch<T>(  // <T> 와 스키마 조드스키마 추가
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

  const res = await fetch(url, { // 한 번 기다리고
    ...options,
    headers,
  });

  // 1. HTTP 에러 처리 추가 (핵심)
  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "API Error");
  }

  const data = await res.json();

  // 2. schema 검증
  if (schema) {
    return schema.parse(data);
  }

  // 3. 타입 보장
  return data as T;
}

