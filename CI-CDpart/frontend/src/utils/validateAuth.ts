// auth.schema.ts
import { z } from "zod";

export const authBaseSchema = z.object({
  username: z.string().min(2, "아이디는 2자 이상"),
  password: z.string().min(3, "비밀번호는 3자 이상"),
});

export type LoginInput = z.infer<typeof authBaseSchema>;
