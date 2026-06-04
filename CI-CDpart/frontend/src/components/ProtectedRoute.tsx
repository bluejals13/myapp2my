import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function ProtectedRoute() {
  const { isLoggedIn, isLoading } = useAuth();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/main";
  
  if (isLoading) return null; // or spinner 으로 돌게
  
  if (!isLoggedIn) {
  return <Navigate to="/login" replace state={{ from: location }} />;
  }

  return <Outlet />;
}
