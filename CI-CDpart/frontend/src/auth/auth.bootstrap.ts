// auth.bootstrap.ts 초기 복구
import { authStorage } from "./auth.storage";
import { apiFetch } from "./api";

export async function initAuth(
  setUser: (u: any) => void,
  setToken: (t: string | null) => void
) {
  const token = authStorage.get();
  if (!token) return;

  try {
    const user = await apiFetch("/api/users/me");

    setToken(token);
    setUser(user);
  } catch {
    authStorage.clear();
    setToken(null);
    setUser(null);
  }
}
