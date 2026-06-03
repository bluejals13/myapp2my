// auth.schema.ts

import { z } from "zod";

// 1. request schema (로그인 입력값)
export const authBaseSchema = z.object({
  username: z
    .string()
    .trim()
    .min(2, "아이디는 2자 이상"),

  password: z
    .string()
    .trim()
    .min(3, "비밀번호는 3자 이상"),
});


export const loginSchema = authBaseSchema; // 구조 개선 로그인 인풋 > 로그인 스키마

export type LoginInput = z.infer<typeof loginSchema>;