import { Navigate, Outlet } from "react-router-dom";
import FullPageSpinner from "../components/FullPageSpinner";
import { useAuth } from "../auth/AuthContext";


export default function ProtectedRoute( {	// 깜박거리는 거 로딩화면으로 수정
  permission, }: { permission?: string; }) {
  
  const { isLoggedIn, isLoading, hasPermission } = useAuth();
  
  if (isLoading) return <FullPageSpinner />;
  
  if (!isLoggedIn) {
  return <Navigate to="/login" replace />;
  }
  
  if (permission && !hasPermission(permission)) {
    return <Forbidden403 />;
  }
  
  return <Outlet />;
}
