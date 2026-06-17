import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import "./Main.css";

export default function Main() {
  const navigate = useNavigate();
  const { hasPermission } = useAuth();
  return (
    <div className="main">

      {/* HERO */}
      <section className="hero">
        <h1>🚀 Service Dashboard</h1>
        <p>페이지 연결 구조 중심 메인 화면</p>
      </section>

      {/* NAV GRID */}
      <section className="grid">
        {hasPermission("MENU_READ") && (
        <div className="card" onClick={() => navigate("/menu")}>
          <h2>📦 Menu</h2>
          <p>메뉴 관리 페이지</p>
        </div> )}
        {hasPermission("PERMISSION_READ") && (
        <div className="card" onClick={() => navigate("/permission")}>
          <h2>🔐 Permission</h2>
          <p>권한 관리 페이지</p>
        </div> )}
        {hasPermission("USER_READ") && (
        <div className="card" onClick={() => navigate("/user")}>
          <h2>👤 User Admin</h2>
          <p>유저 관리 페이지</p>
        </div> )}
        
        <div className="card debug" onClick={() => navigate("/about")}>
          <h2>🧪 Debug</h2>
          <p>RBAC / Auth 확인</p>
        </div> 

      </section>

      {/* FOOTER */}
      <footer className="footer">
        © 2026 Service System
      </footer>

    </div>
  );
}
