import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Orders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const res = await api.get("/orders");
      console.log("ORDERS:", res.data);
      setOrders(res.data);
    } catch (err) {
      console.log("Error fetching orders", err);
    }
  };


  const logout = () => {
        localStorage.removeItem("token");
        window.location.href = "/login";
        };

  if (!orders) return <div>Loading...</div>;

  return (
    <div>
      <h2>Your Orders</h2>

      {orders.length === 0 ? (
        <p>No orders yet</p>
      ) : (
        orders.map((order) => (
          <div key={order.id} style={{ border: "1px solid black", margin: "10px", padding: "10px" }}>
            
            <h3>Order #{order.id}</h3>
            <p>Status: {order.status}</p>
            <p>Total: {order.totalAmount}</p>

            <h4>Items:</h4>
            {order.items.map((item, index) => (
              <div key={index}>
                <p>{item.productName} x {item.quantity}</p>
              </div>
            ))}
          </div>
        ))
      )}
      <button onClick={logout}>Logout</button>
    </div>
  );
}