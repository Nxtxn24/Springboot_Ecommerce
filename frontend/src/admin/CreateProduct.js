import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api/axios";

export default function CreateProduct() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: "",
    category: "",
    stockQuantity: "",
    imageUrl: ""
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {

    const { name, value } = e.target;

    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    setLoading(true);

    try {

      await api.post(
        "/admin/products",
        {
          ...formData,
          price: Number(formData.price),
          stockQuantity: Number(formData.stockQuantity)
        }
      );

      alert("Product created successfully");

      navigate("/admin/products");

    } catch (err) {

      console.log(err);

      alert("Failed to create product");

    } finally {

      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      <div className="max-w-2xl mx-auto bg-white p-6 rounded-lg shadow">

        <h1 className="text-3xl font-bold mb-6">
          Create Product
        </h1>

        <form
          onSubmit={handleSubmit}
          className="space-y-4"
        >

          {/* Name */}
          <div>

            <label className="block mb-1 font-medium">
              Product Name
            </label>

            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              className="w-full border rounded px-3 py-2"
            />

          </div>

          {/* Description */}
          <div>

            <label className="block mb-1 font-medium">
              Description
            </label>

            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              required
              rows="4"
              className="w-full border rounded px-3 py-2"
            />

          </div>

          {/* Price */}
          <div>

            <label className="block mb-1 font-medium">
              Price
            </label>

            <input
              type="number"
              name="price"
              value={formData.price}
              onChange={handleChange}
              required
              min="0"
              step="0.01"
              className="w-full border rounded px-3 py-2"
            />

          </div>

          {/* Category */}
          <div>

            <label className="block mb-1 font-medium">
              Category
            </label>

            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
              className="w-full border rounded px-3 py-2"
            />

          </div>

          {/* Stock Quantity */}
          <div>

            <label className="block mb-1 font-medium">
              Stock Quantity
            </label>

            <input
              type="number"
              name="stockQuantity"
              value={formData.stockQuantity}
              onChange={handleChange}
              required
              min="0"
              className="w-full border rounded px-3 py-2"
            />

          </div>

          <div>
            <label className="block mb-1 font-medium">
                Product Image URL
            </label>

            <input
                type="text"
                name="imageUrl"
                value={formData.imageUrl}
                onChange={handleChange}
                className="w-full border rounded px-3 py-2"
                placeholder="https://..."
            />
            </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={loading}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
          >
            {loading ? "Creating..." : "Create Product"}
          </button>

        </form>

      </div>

    </div>
  );
}