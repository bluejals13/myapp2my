import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../api";

import { useAuth } from "../auth/AuthContext";

import {
  loginResponseSchema,
  type LoginResponse,
} from "../auth/auth.response";

import "./Login.css";
// import styles from "./Login.module.css";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async () => {
      const result = loginSchema.safeParse({ username, password });

      if (!result.success) {
        const errors = result.error.flatten().fieldErrors;

        setErrorMessage(
          errors.username?.[0] ??
          errors.password?.[0] ??
          "입력값 오류"
        );
        return;
      }

    try {
      setIsLoading(true);
      setErrorMessage("");

      const data = await apiFetch<LoginResponse>("/api/auth/login");

      await login(data.accessToken);
      navigate("/main");
    } catch {
      setErrorMessage("로그인 실패");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="login-container">
      <form
        className="login-form"
        onSubmit={(e) => {
        e.preventDefault();
        handleLogin();
        }}
      >
      <h2>로그인</h2>
      
      <div className="form-group">
        <label htmlFor="username">아이디</label>
        <input
          id="username"
          type="text"
          placeholder="아이디를 입력하세요"
          value={username}
          autoComplete="username"
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>

      <div className="form-group">
        <label htmlFor="password">비밀번호</label>
        <input
          id="password"
          type="password"
          placeholder="비밀번호를 입력하세요"
          value={password}
          autoComplete="current-password"
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

        {errorMessage && (
          <p className="error-message">
            {errorMessage}
          </p>
        )}
        
        <button
          type="submit"
          disabled={isLoading || !username || !password}
          >{isLoading ? "로그인 중..." : "로그인"}
        </button>
      </form>
    </div>
  );
}
