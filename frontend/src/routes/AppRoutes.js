import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import Login from "../pages/Login";
import Products from "../pages/Products";
import Cart from "../pages/Cart";
import Orders from "../pages/Orders";
import ProtectedRoute from "./ProtectedRoute";
import Navbar from "./Navbar";
import AdminOrders from "../pages/AdminOrders";
import Register from "../pages/Register";
import { Navigate } from "react-router-dom";

function Layout() {
  const location = useLocation();

  const authPages = ["/login", "/register"];

  const hideNavbar = authPages.includes(location.pathname);

  return (
    <>
      {!hideNavbar && <Navbar />}

      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        {/* PUBLIC ROUTE */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* PROTECTED ROUTES */}
        <Route
          path="/products"
          element={
            <ProtectedRoute>
              <Products />
            </ProtectedRoute>
          }
        />

        <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          }
        />

        <Route
          path="/orders"
          element={
            <ProtectedRoute role="USER">
              <Orders />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/orders"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminOrders />
            </ProtectedRoute>
          }
        />
        
      </Routes>
    </>
  );
}

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Layout />
    </BrowserRouter>
  );
}