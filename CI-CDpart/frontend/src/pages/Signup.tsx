import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

import { apiFetch } from "../api";
import { signupSchema } from "../auth/auth.schema";

import "./Auth.css";
// import styles from "./Login.module.css";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const handleSignup = async () => {
    const result = signupSchema.safeParse({
      username,
      password,
      email,
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

      await apiFetch<void>("/api/auth/signup", undefined, {
        method: "POST",
        body: JSON.stringify(result.data),
      });

      navigate("/login");
      
    } catch (e: any) {
        const msg =
        e?.message === "DUPLICATE_USERNAME"
        ? "이미 존재하는 아이디입니다"
        : "회원가입 실패";
      setErrorMessage(msg);
  } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <form
        className="auth-form"
        onSubmit={(e) => {
          if (e.key === "Enter") handleSignup();
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
            autoComplete="new-password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>이메일</label>
          <input
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        
        {errorMessage && (
          <p className="error-message">{errorMessage}</p>
        )}
        <p className="switch-auth">
          이미 계정이 있나요? <Link to="/login">로그인</Link>
        </p>

        <button disabled={isLoading || !username || !password || !email}>
          {isLoading ? "가입 중..." : "회원가입"}
        </button>
      </form>
    </div>
  );
}
