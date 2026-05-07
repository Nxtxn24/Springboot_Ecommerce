import { useEffect, useState } from "react";
import { api } from "../api/axios";
import { updateOrderStatus } from "../api/orders";

export default function AdminOrders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get("/admin/orders")
      .then(res => setOrders(res.data));
  }, []);

  const handleStatusChange = async (orderId, status) => {
    try {
      await updateOrderStatus(orderId, status);

      // update UI without reload
      setOrders(prev =>
        prev.map(order =>
          order.id === orderId
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
    <div>
      <h2>Admin Orders</h2>

      {orders.map(order => (
        <div key={order.id} style={{ marginBottom: "10px" }}>
          <p>Order ID: {order.id}</p>
          <p>Status: {order.status}</p>

          <select
            value={order.status}
            onChange={(e) =>
              handleStatusChange(order.id, e.target.value)
            }
          >
            <option value="PENDING">PENDING</option>
            <option value="SHIPPED">SHIPPED</option>
            <option value="DELIVERED">DELIVERED</option>
          </select>
        </div>
      ))}
    </div>
  );
}