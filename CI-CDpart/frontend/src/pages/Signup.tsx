import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { apiFetch } from "../api";
import { signupSchema } from "../auth/auth.schema";

import "./Login.css";
// import styles from "./Login.module.css";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const handleSignup = async () => {
    const result = signupSchema.safeParse({ username, password });

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

      await apiFetch("/api/auth/signup", undefined, {
        method: "POST",
        body: JSON.stringify(result.data),
      });

      navigate("/login");
    } catch {
      setErrorMessage("회원가입 실패");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="signup-container">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSignup();
        }}
      >
      <h2>회원가입</h2>

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

        {/* ✅ 빨간 에러 메시지 영역 */}
        {errorMessage && (
          <p className="error-message">{errorMessage}</p>
        )}

        <button disabled={isLoading}>
          {isLoading ? "가입 중..." : "회원가입"}
        </button>
      </form>
    </div>
  );
}
