import { logout } from "../utils/auth";
import { useNavigate, Link } from "react-router-dom";

export default function UserNavbar() {
  const navigate = useNavigate();
  const email = localStorage.getItem("userEmail");
  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    
    <div style={{ padding: "10px", borderBottom: "1px solid black" }}>
      <Link to="/products" style={{ marginRight: "10px" }}>
        Products
      </Link>

      <Link to="/cart" style={{ marginRight: "10px" }}>
        Cart
      </Link>

      <Link to="/orders" style={{ marginRight: "10px" }}>
        Orders
      </Link>

      <button onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}