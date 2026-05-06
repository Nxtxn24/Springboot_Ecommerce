import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Cart() {
  const [cart, setCart] = useState(null);

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      const res = await api.get("/cart");
      setCart(res.data);
    } catch (err) {
      console.log("Error fetching cart", err);
    }
  };

  const handleCheckout = async () => {
        try {
            await api.post("/orders/checkout");

            alert("Order placed successfully");

            // refresh cart
            fetchCart();

        } catch (err) {
            console.log("Checkout error", err);
        }
        };

  if (!cart) return <div>Loading...</div>;

  return (
    <div>
      <h2>Your Cart</h2>

      {cart.items.map((item) => (
        <div key={item.productId}>
          <h3>{item.productName}</h3>
          <p>Quantity: {item.quantity}</p>

        <button onClick={handleCheckout}>
            Checkout
            </button>
        </div>

      ))}
    </div>
  );
}