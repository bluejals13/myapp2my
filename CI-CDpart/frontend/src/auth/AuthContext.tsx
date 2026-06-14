// AuthContext.tsx UI 상태
import { createContext, useContext, useEffect, useState } from "react";
import { apiFetch } from "../api";
import { authStorage } from "./auth.storage";	// jwt 토큰 키 get, set, clear
import { apiWithAuth } from "./auth.interceptor";

export type User = {	// 👈 RBAC 추가 수정 부분
  id: number;
  username: string;
  roles: string[];        // 👈 추가 (ADMIN, USER, MODERATOR)
  permissions: string[];  // 👈 선택 (USER_READ, USER_WRITE ...)
};

type AuthContextType = {
  token: string | null;
  user: User | null;
  isLoggedIn: boolean;
  isLoading: boolean;   // ← 추가

  login: (token: string) => Promise<void>;
  logout: () => void;
  refreshUser: () => Promise<void>;

  hasRole: (role: string) => boolean;           // 👈 추가
  hasPermission: (p: string) => boolean;        // 👈 추가
};

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(authStorage.get());
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  
  const isLoggedIn = !!token;

const hasRole = (role: string) =>       // 👈 추가
  (user?.roles ?? []).some(r => r.toLowerCase() === role.toLowerCase());

const hasPermission = (p: string) =>		// 👈 추가
  (user?.permissions ?? []).includes(p);

  const refreshUser = async () => {
    try {
      const data = await apiWithAuth<User>("/api/users/me");
      setUser(data);
    } catch (e: any) {
    	if (e?.status === 401) {
      	logout();
      }
    }
  };

  const login = async (newToken: string) => {
  authStorage.set(newToken);
  setToken(newToken);

  try {
      const me = await apiFetch<User>("/api/users/me");
      setUser(me);
    } catch (e) {
      authStorage.clear();
      setToken(null);
      setUser(null);
      throw e;
    }
  };

  const logout = () => {
    authStorage.clear();
    setToken(null);
    setUser(null);
  };

// 초기 로그인 복구
useEffect(() => {
  const init = async () => {
    try {
      if (authStorage.get()) {
        await refreshUser();
      }
    } finally {
      setIsLoading(false);
    }
  };

  init();
}, []);

// refresh 실패 로그아웃 동기화
useEffect(() => {
  const handler = () => {
    logout();
  };

  window.addEventListener("auth:logout", handler);

  return () => {
    window.removeEventListener("auth:logout", handler);
  };
}, []);

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        isLoggedIn,
        isLoading,   // ← 이거 필수
        login,
        logout,
        refreshUser,
        hasRole,		// 👈 추가
        hasPermission,	// 👈 추가
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
