import { useEffect, useState } from "react";
import { api } from "../api/axios";
import { updateOrderStatus, getOrderStatuses } from "../api/orders";

export default function AdminOrders() {
  const [orders, setOrders] = useState([]);
  const [statuses, setStatuses] = useState([]);

  // 🧠 Fetch orders + statuses
  useEffect(() => {
    api.get("/admin/orders")
      .then(res => {
        setOrders(res.data);
      })
      .catch(err => console.log(err));

    getOrderStatuses()
      .then(res => {
        setStatuses(res.data);
      })
      .catch(err => console.log(err));

  }, []);

  const handleStatusChange = async (orderId, status) => {
    try {
      await updateOrderStatus(orderId, status);

      // update UI without reload
      setOrders(prev =>
        prev.map(order =>
          order.orderId === orderId
            ? { ...order, status }
            : order
        )
      );

    } catch (err) {
      console.log(err);
      alert("Failed to update status");
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      {/* Header */}
      <div className="mb-6">
        <h2 className="text-2xl font-bold text-gray-800">
          Admin Orders
        </h2>
        <p className="text-gray-500">
          Manage and update all customer orders
        </p>
      </div>

      {/* Orders Grid */}
      <div className="space-y-4">
        {orders.map(order => (
          <div
            key={order.orderId}
            className="bg-white shadow-md rounded-xl p-5 flex flex-col md:flex-row md:items-center md:justify-between"
          >

            {/* Left Info */}
            <div className="space-y-1">
              <p className="text-gray-700 font-semibold">
                Order #{order.orderId}
              </p>
              <p className="text-sm text-gray-500">
                User ID:{" "}
                <span className="font-medium text-gray-700">
                  {order.userId}
                </span>
              </p>

              <p className="text-sm text-gray-500">
                Status:{" "}
                <span
                  className={`font-medium px-2 py-1 rounded-full text-xs
                    ${
                      order.status === "DELIVERED"
                        ? "bg-green-100 text-green-700"
                        : order.status === "SHIPPED"
                        ? "bg-blue-100 text-blue-700"
                        : order.status === "CANCELLED"
                        ? "bg-red-100 text-red-700"
                        : "bg-yellow-100 text-yellow-700"
                    }
                  `}
                >
                  {order.status}
                </span>
              </p>
            </div>

            {/* Right Action */}
            <div className="mt-3 md:mt-0">
              <select
                value={order.status}
                onChange={(e) =>
                  handleStatusChange(order.orderId, e.target.value)
                }
                className="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
              >
                {statuses.map(status => (
                  <option key={status} value={status}>
                    {status}
                  </option>
                ))}
              </select>
            </div>

          </div>
        ))}
      </div>
    </div>
  );
}