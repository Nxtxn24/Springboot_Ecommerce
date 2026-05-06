import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setLoading(true);
    try {
      const res = await api.get("/orders");
      setOrders(res.data);
    } catch (err) {
      console.log("Error fetching orders", err);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  if (loading) {
    return (
      <div className="text-center mt-10 text-gray-500">
        Loading orders...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      {/* Header */}
      <div className="flex justify-between items-center max-w-4xl mx-auto mb-6">
        <h2 className="text-2xl font-bold">Your Orders</h2>

        <button
          onClick={logout}
          className="bg-red-500 text-white px-4 py-2 rounded-md 
                     hover:bg-red-600 transition"
        >
          Logout
        </button>
      </div>

      {/* Empty state */}
      {orders.length === 0 ? (
        <p className="text-center text-gray-500">
          No orders yet 📦
        </p>
      ) : (
        <div className="max-w-4xl mx-auto space-y-6">

          {orders.map((order) => (
            <div
              key={order.id}
              className="bg-white rounded-xl shadow-md p-5"
            >

              {/* Order Header */}
              <div className="flex justify-between items-center mb-4">
                <h3 className="font-semibold text-lg">
                  Order #{order.id}
                </h3>

                <span className="text-sm px-3 py-1 rounded-full bg-blue-100 text-blue-600">
                  {order.status}
                </span>
              </div>

              {/* Order Info */}
              <p className="text-gray-600 mb-4">
                Total: ₹{order.totalAmount}
              </p>

              {/* Items */}
              <div className="border-t pt-3 space-y-2">
                <h4 className="font-medium mb-2">Items</h4>

                {order.items.map((item, index) => (
                  <div
                    key={index}
                    className="flex justify-between text-sm text-gray-700"
                  >
                    <span>{item.productName}</span>
                    <span>x {item.quantity}</span>
                  </div>
                ))}
              </div>

            </div>
          ))}

        </div>
      )}
    </div>
  );
}