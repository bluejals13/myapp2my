import { createContext, useContext, useEffect, useState } from "react";
import { apiFetch } from "../api";
import { authStorage } from "./auth.service";

export type User = {
  id: number;
  username: string;
};

type AuthContextType = {
  token: string | null;
  user: User | null;
  isLoggedIn: boolean;

  login: (token: string) => Promise<void>;
  logout: () => void;
  refreshUser: () => Promise<void>;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() =>
    authStorage.getToken()
  );

  const [user, setUser] = useState<User | null>(null);

  const isLoggedIn = !!token;

  // 🔥 /me 호출
  const refreshUser = async () => {
    if (!token) return;

    try {
      const data = await apiFetch<User>("/api/users/me");
      // console.log(data);
      setUser(data);
    } catch {
      setUser(null);
    }
  };

  // 🔥 login
  const login = async (newToken: string) => {
    setToken(newToken);
    authStorage.set(newToken);

    await refreshUser();
  };

  // 🔥 logout
  const logout = () => {
    setToken(null);
    setUser(null);
    authStorage.clear();
  };

  // 🔥 앱 시작 시 자동 user 복구
  useEffect(() => {
    if (token) {
      refreshUser();
    }
  }, [token]);

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
