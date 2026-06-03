// auth.schema.ts

import { z } from "zod";

// 로그인 / 회원가입 공통 input
export const authBaseSchema = z.object({
  username: z.string().trim().min(2, "아이디는 2자 이상"),
  password: z.string().trim().min(3, "비밀번호는 3자 이상"),
});

export const loginSchema = authBaseSchema;
export const signupSchema = authBaseSchema;

export type LoginInput = z.infer<typeof loginSchema>;
export type SignupInput = z.infer<typeof signupSchema>;
