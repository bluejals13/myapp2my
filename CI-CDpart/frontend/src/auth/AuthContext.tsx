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

export function AuthProvider({ children }) {
  const [token, setToken] = useState(authStorage.get());
  const [user, setUser] = useState(null);

  const [isLoading, setIsLoading] = useState(true);

  const isLoggedIn = !!token;

  // ✅ /me 호출 (중복 방지 + 안정화)
  const refreshUser = async () => {
    const data = await apiFetch("/api/users/me");

        setUser(data);
      } catch (err) {
      console.error("refreshUser failed:", err);
      setUser(data);
    }
  }

  // ✅ login 문제 없음
  const login = (newToken: string) => {
    authStorage.set(newToken);
    setToken(newToken);
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
