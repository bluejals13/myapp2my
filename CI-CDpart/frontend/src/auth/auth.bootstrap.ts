// auth.bootstrap.ts
import { authStorage } from "./auth.storage";
import { apiFetch } from "./api";

export async function initAuth(setUser, setToken) {
  const token = authStorage.get();
  if (!token) return;

  try {
    const user = await apiFetch("/api/users/me");
    setUser(user);
    setToken(token);
  } catch {
    authStorage.clear();
  }
}
