import { Navigate } from "react-router-dom";
import { getStoredUser } from "../utils/auth";

export default function ProtectedRoute({ children, role }) {
  const user = getStoredUser();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (role && user.role !== role) {
    const destination = user.role === "ADMIN" ? "/admin/orders" : "/products";
    return <Navigate to={destination} replace />;
  }

  return children;
}
