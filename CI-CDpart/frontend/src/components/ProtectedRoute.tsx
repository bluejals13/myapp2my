import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function ProtectedRoute() {
  const { isLoggedIn, isLoading } = useAuth();
  
  if (isLoading) return null; // or spinner 으로 돌게
  
  if (!isLoggedIn) {
  return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}
