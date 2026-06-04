import { createContext, useContext, useEffect, useState, useCallback } from "react";
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
  isLoading: boolean;

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
  const [isLoading, setIsLoading] = useState(true);

  const isLoggedIn = !!token;

  // ✅ /me 호출 (중복 방지 + 안정화)
  const refreshUser = useCallback(async () => {
    if (!authStorage.getToken()) return;

    try {
      const data = await apiFetch<User>("/api/users/me", undefined, {
        method: "GET",
        });

        setUser(data);
      } catch (err) {
      console.error("refreshUser failed:", err);
      setUser(null);
    }
  }, []);

  // ✅ login 문제 없음
  const login = async (newToken: string) => {
    authStorage.set(newToken);
    setToken(newToken);

    await refreshUser();
  };

  // ✅ logout
  const logout = () => {
    authStorage.clear();
    setToken(null);
    setUser(null);
  };

  // ✅ 앱 최초 로딩 시 1회만 user 복구
  useEffect(() => {
    const init = async () => {
      if (authStorage.getToken()) {
        await refreshUser();
      }
      setIsLoading(false);
    };

    init();
  }, [refreshUser]);

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        isLoggedIn,
        isLoading,
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
