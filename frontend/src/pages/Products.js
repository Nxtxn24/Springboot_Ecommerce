import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Products() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/api/products");
      setProducts(res.data);
    } catch (err) {
      console.log("Error fetching products", err);
    }
  };

  // ✅ ADD THIS FUNCTION (you were missing it)
  const addToCart = async (productId) => {
    try {
      await api.post(`/cart/add/${productId}?quantity=1`);

      alert("Added to cart");
    } catch (err) {
      console.log("Error adding to cart", err);
    }
  };

  return (
    <div>
      <h2>Products</h2>

      {products.map((p) => (
        <div key={p.id}>
          <h3>{p.name}</h3>
          <p>Price: {p.price}</p>

          {/* ✅ Button INSIDE same parent */}
          <button onClick={() => addToCart(p.id)}>
            Add to Cart
          </button>
        </div>
      ))}
    </div>
  );
}