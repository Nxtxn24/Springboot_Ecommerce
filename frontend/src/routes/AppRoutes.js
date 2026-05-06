import { BrowserRouter, Routes, Route } from "react-router-dom";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<div>Login</div>} />
        <Route path="/products" element={<div>Products</div>} />
        <Route path="/cart" element={<div>Cart</div>} />
        <Route path="/orders" element={<div>Orders</div>} />
      </Routes>
    </BrowserRouter>
  );
}