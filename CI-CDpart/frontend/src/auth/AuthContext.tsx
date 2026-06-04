// AuthContext.tsx UI 상태
import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import { apiFetch } from "../api";
import { authStorage } from "./auth.storage";

export type User = {
  id: number;
  username: string;
};

type AuthContextType = {
  token: string | null;
  user: User | null;
  isLoggedIn: boolean;

  login: (token: string) => void;
  logout: () => void;
  refreshUser: () => Promise<void>;
};

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(authStorage.get());
  const [user, setUser] = useState<User | null>(null);

  const isLoggedIn = !!token;

  const refreshUser = async () => {
    if (!authStorage.get()) return;

    try {
      const data = await apiFetch<User>("/api/users/me");
      setUser(data);
    } catch {
      setUser(null);
    }
  };

  const login = (newToken: string) => {
    authStorage.set(newToken);
    setToken(newToken);
  };

  const logout = () => {
    authStorage.clear();
    setToken(null);
    setUser(null);
  };

  useEffect(() => {
    if (token) refreshUser();
  }, [token]);

  useEffect(() => {
    const handler = () => logout();

    window.addEventListener("auth:logout", handler);
    return () => window.removeEventListener("auth:logout", handler);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        isLoggedIn,
        login,
        logout,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("AuthProvider 필요");
  return ctx;
}
