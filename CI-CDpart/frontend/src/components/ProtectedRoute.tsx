import { Navigate, Outlet } from "react-router-dom";
import FullPageSpinner from "../components/FullPageSpinner";
import { useAuth } from "../auth/AuthContext";

export default function ProtectedRoute() {	// 깜박거리는 거 로딩화면으로 수정
  const { isLoggedIn, isLoading } = useAuth();
  
  if (isLoading) return <FullPageSpinner />;
  
  if (!isLoggedIn) {
  return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}
