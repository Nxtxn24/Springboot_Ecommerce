import { Link, useNavigate } from "react-router-dom";
import { logout } from "../utils/auth";

export default function AdminNavbar() {

  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div style={{ padding: "10px", borderBottom: "1px solid black" }}>

      <Link to="/admin/products" style={{ marginRight: "10px" }}>
        Products
      </Link>

      <Link to="/admin/orders" style={{ marginRight: "10px" }}>
        Orders
      </Link>

      <button onClick={handleLogout}>
        Logout
      </button>

    </div>
  );
}