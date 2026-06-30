import UserNavbar from "./UserNavbar";
import AdminNavbar from "./AdminNavbar";
import { getStoredUser } from "../utils/auth";

export default function Navbar() {

  const role = getStoredUser()?.role;

  if (role === "ADMIN") {
    return <AdminNavbar />;
  }

  return <UserNavbar />;
}
