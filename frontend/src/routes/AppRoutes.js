import { BrowserRouter, Navigate, Routes, Route, useLocation } from "react-router-dom";
import CreateProduct from "../admin/CreateProduct";
import EditProduct from "../admin/EditProduct";
import Login from "../pages/Login";
import Products from "../pages/Products";
import Cart from "../pages/Cart";
import Orders from "../pages/Orders";
import ProtectedRoute from "./ProtectedRoute";
import AdminOrders from "../pages/AdminOrders";
import AdminProducts from "../pages/AdminProducts";
import Register from "../pages/Register";
import Navbar from "./Navbar";

function Layout() {

  const location = useLocation();

  const authPages = ["/login", "/register"];

  const hideNavbar = authPages.includes(location.pathname);

  return (
    <>
      {!hideNavbar && <Navbar />}

      <Routes>

        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* PUBLIC */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* USER */}
        <Route
          path="/products"
          element={<ProtectedRoute role="USER"><Products /></ProtectedRoute>}
        />

        <Route
          path="/cart"
          element={<ProtectedRoute role="USER"><Cart /></ProtectedRoute>}
        />

        <Route
          path="/orders"
          element={<ProtectedRoute role="USER"><Orders /></ProtectedRoute>}
        />

        {/* ADMIN */}
        <Route
          path="/admin/orders"
          element={<ProtectedRoute role="ADMIN"><AdminOrders /></ProtectedRoute>}
        />

        <Route
          path="/admin/products"
          element={<ProtectedRoute role="ADMIN"><AdminProducts /></ProtectedRoute>}
        />

        <Route
          path="/admin/products/new"
          element={<ProtectedRoute role="ADMIN"><CreateProduct /></ProtectedRoute>}
        />

        <Route
          path="/admin/products/edit/:id"
          element={<ProtectedRoute role="ADMIN"><EditProduct /></ProtectedRoute>}
        />

      </Routes>
    </>
  );
}

export default function AppRoutes() {
  return (
    <BrowserRouter future={{ v7_startTransition: true, v7_relativeSplatPath: true }}>
      <Layout />
    </BrowserRouter>
  );
}
