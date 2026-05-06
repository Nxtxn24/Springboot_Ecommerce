import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Cart() {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    setLoading(true);
    try {
      const res = await api.get("/cart");
      setCart(res.data);
    } catch (err) {
      console.log("Error fetching cart", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCheckout = async () => {
    try {
      await api.post("/orders/checkout");

      alert("Order placed successfully");

      fetchCart();
    } catch (err) {
      console.log("Checkout error", err);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10 text-gray-500">
        Loading cart...
      </div>
    );
  }

  if (!cart || cart.items.length === 0) {
    return (
      <div className="text-center mt-10 text-gray-500">
        Your cart is empty 🛒
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      {/* Title */}
      <h2 className="text-2xl font-bold text-center mb-6">
        Your Cart
      </h2>

      {/* Cart Items */}
      <div className="max-w-2xl mx-auto space-y-4">

        {cart.items.map((item) => (
          <div
            key={item.productId}
            className="bg-white p-4 rounded-lg shadow flex justify-between items-center"
          >

            {/* Left */}
            <div>
              <h3 className="font-semibold text-lg">
                {item.productName}
              </h3>

              <p className="text-gray-600">
                Quantity: {item.quantity}
              </p>
            </div>

          </div>
        ))}

        {/* Checkout Section (OUTSIDE LOOP — IMPORTANT) */}
        <div className="bg-white p-4 rounded-lg shadow flex justify-between items-center">

          <span className="font-semibold">
            Ready to checkout?
          </span>

          <button
            onClick={handleCheckout}
            className="bg-green-600 text-white px-4 py-2 rounded-md 
                       hover:bg-green-700 transition"
          >
            Checkout
          </button>

        </div>

      </div>
    </div>
  );
}