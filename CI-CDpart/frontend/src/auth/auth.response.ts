// authresponse.ts

import { z } from "zod";

// 로그인 응답
export const loginResponseSchema = z.object({
  accessToken: z.string(),
  grantType: z.string(),
});

export type LoginResponse = z.infer<typeof loginResponseSchema>;
