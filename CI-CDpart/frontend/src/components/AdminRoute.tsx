import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function AdminRoute() {	// 접근 admin 권한 없는 경우
  const { user, isLoading, hasRole } = useAuth();

  if (isLoading) return <FullPageSpinner />;

  if (!user) return <Navigate to="/login" replace />;

  if (!hasRole("ROLE_ADMIN") && !hasRole("ROLE_MODERATOR")) {
    return <Navigate to="/403" replace />;
  }

  return <Outlet />;
}