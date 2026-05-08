import { parseJwt } from "../utils/jwt";
import { useState, useEffect } from "react";
import { api } from "../api/axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { getAuthUser } from "../utils/authHelper";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const user = getAuthUser();

    if (user) {
      if (user.role === "ADMIN") {
        navigate("/admin/orders");
      } else {
        navigate("/products");
      }
    }
  }, [navigate]);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await api.post("/auth/login", {
        email,
        password,
      });

      const token = response.data;

      localStorage.setItem("token", token);

      const user = getAuthUser();

      if (!user) throw new Error("Invalid token");

      localStorage.setItem("role", user.role);
      localStorage.setItem("email", user.sub);

      if (user.role === "ADMIN") {
        navigate("/admin/orders");
      } else {
        navigate("/products");
      }

    } catch (err) {
      console.log(err);
      alert("Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      
      <div className="w-full max-w-md bg-white p-8 rounded-xl shadow-lg">
        
        
        <h2 className="text-2xl font-bold text-center mb-6">
          Login to your account
        </h2>

        
        <form onSubmit={handleLogin} className="space-y-4">

          
          <div>
            <label className="block text-sm font-medium mb-1">
              Email
            </label>
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full border border-gray-300 rounded-md px-3 py-2 
                         focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          
          <div>
            <label className="block text-sm font-medium mb-1">
              Password
            </label>
            <input
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full border border-gray-300 rounded-md px-3 py-2 
                         focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-2 rounded-md 
                       font-medium hover:bg-blue-700 transition 
                       disabled:opacity-50"
          >
            {loading ? "Logging in..." : "Login"}
          </button>

        </form>

        <div className="mt-4 text-center">
          <p className="text-sm text-gray-600">
            Don’t have an account?
            <Link
              to="/register"
              className="text-blue-600 font-medium ml-1 hover:underline"
            >
              Register
            </Link>
          </p>
        </div>

      </div>
    </div>
  );
}

  


  