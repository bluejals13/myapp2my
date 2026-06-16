import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { apiFetch } from "../api";
import { useAuth } from "../auth/AuthContext";

import { loginSchema } from "../auth/auth.schema";

import "./Auth.css";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async () => {
      const result = loginSchema.safeParse({
        username,
        password,
        });

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

      // 2. API 호출 (POST + body 필수)
    const data = await apiFetch<{ accessToken: string }>(
      "/api/auth/login",
      {
        method: "POST",
        body: JSON.stringify(result.data),
      }
    );

      // 3. login (token 저장 + /me 호출)
      await login(data.accessToken);

      // 4. 이동
      navigate("/");

    } catch (e: any) {
        setErrorMessage(
          e?.message === "INVALID_CREDENTIALS"
            ? "아이디 또는 비밀번호가 틀렸습니다"
            : "로그인 실패"
        );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <form
        className="auth-form"
        onSubmit={(e) => {
          e.preventDefault();
          handleLogin();
        }}
      >
        <h2>로그인</h2>

        <div className="form-group">
          <label>아이디</label>
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>비밀번호</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        {errorMessage && (
          <p className="error-message">{errorMessage}</p>
        )}

        <button disabled={isLoading}>
          {isLoading ? "로그인 중..." : "로그인"}
        </button>
      </form>
    </div>
  );
}
