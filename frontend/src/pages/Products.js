import { useEffect, useState } from "react";
import { api } from "../api/axios";

export default function Products() {

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const res = await api.get("/api/products");
      setProducts(res.data);
    } catch (err) {
      console.log("Error fetching products", err);
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (productId) => {
    try {
      await api.post(`/cart/add/${productId}?quantity=1`);
      alert("Added to cart");
    } catch (err) {
      console.log("Error adding to cart", err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      {/* Header */}
      <h2 className="text-2xl font-bold mb-6 text-center">
        Products
      </h2>

      {/* Loading */}
      {loading && (
        <p className="text-center text-gray-500">
          Loading products...
        </p>
      )}

      {/* Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">

        {products.map((p) => (

          <div
            key={p.id}
            className="bg-white rounded-xl shadow-md hover:shadow-lg transition p-4 flex flex-col"
          >

            {/* Image */}
            <img
              src={p.imageUrl}
              alt={p.name}
              className="w-full h-40 object-cover rounded-md mb-3"
            />

            {/* Product Name */}
            <h3 className="text-lg font-semibold mb-2">
              {p.name}
            </h3>

            {/* Price */}
            <p className="text-gray-600 mb-4">
              ₹{p.price}
            </p>

            {/* Spacer */}
            <div className="flex-grow" />

            {/* Button */}
            <button
              onClick={() => addToCart(p.id)}
              className="bg-blue-600 text-white py-2 rounded-md 
                         hover:bg-blue-700 transition"
            >
              Add to Cart
            </button>

          </div>
        ))}

      </div>
    </div>
  );
}