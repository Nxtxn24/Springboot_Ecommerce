import UserNavbar from "./UserNavbar";
import AdminNavbar from "./AdminNavbar";

export default function Navbar() {

  const role = localStorage.getItem("role");

  if (role === "ADMIN") {
    return <AdminNavbar />;
  }

  return <UserNavbar />;
}