// authresponse.ts

import { z } from "zod";

// 로그인 응답
export const loginResponseSchema = z.object({
  token: z.string(),
  username: z.string(),
});

export type LoginResponse = z.infer<typeof loginResponseSchema>;
