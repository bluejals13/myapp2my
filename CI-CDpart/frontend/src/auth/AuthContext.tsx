// AuthContext.tsx + // authProvider.txs 결합

import { createContext, useContext, useState } from "react";
import { authStorage } from "../auth/auth.service";

import type { LoginResponse } from "../auth/auth.response";

type AuthContextType = {
  token: string | null;
  username: string | null;
  isLoggedIn: boolean;
  login: (data: LoginResponse) => void;
  logout: () => void;
};


const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() =>
    authStorage.getToken()
  );

  const [username, setUsername] = useState<string | null>(() =>
    authStorage.getUsername()
  );

const login = (data: LoginResponse) => {
  setToken(data.token);
  setUsername(data.username);

  authStorage.set(data.token, data.username);
};

  const logout = () => {
    setToken(null);
    setUsername(null);
    authStorage.clear();
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        username,
        isLoggedIn: !!token,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}