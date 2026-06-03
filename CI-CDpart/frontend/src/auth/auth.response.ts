// auth.response.ts

import { z } from "zod";

// 1. request schema (로그인 출력값)
export const loginResponseSchema = z.object({
  token: z.string(),
  username: z.string(),
});

export type LoginResponse = z.infer<typeof loginResponseSchema>;

