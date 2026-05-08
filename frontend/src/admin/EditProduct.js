import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../api/axios"

export default function EditProduct() {

  const { id } = useParams();

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: "",
    category: ""
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {

    if (!id) return;

    fetchProduct();

    }, [id]);

  const fetchProduct = async () => {

  if (!id) return;

  try {

    const response = await api.get(`/admin/products/${id}`);

    const product = response.data;

    setFormData({
      name: product.name,
      description: product.description,
      price: product.price,
      category: product.category,
      stockQuantity: product.stockQuantity
    });

  } catch (err) {

    console.log(err.response || err);

  } finally {

    setLoading(false);
  }
};

  const handleChange = (e) => {

    const { name, value } = e.target;

    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    try {

      await api.put(
        `/admin/products/${id}`,
        {
          ...formData,
          price: Number(formData.price)
        }
      );

      alert("Product updated successfully");

      navigate("/admin/products");

    } catch (err) {

      console.log(err);

      alert("Failed to update product");
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10 text-gray-500">
        Loading product...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      <div className="max-w-2xl mx-auto bg-white p-6 rounded-lg shadow">

        <h1 className="text-3xl font-bold mb-6">
          Edit Product
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

          {/* Submit */}
          <button
            type="submit"
            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition"
          >
            Update Product
          </button>

        </form>

      </div>

    </div>
  );
}