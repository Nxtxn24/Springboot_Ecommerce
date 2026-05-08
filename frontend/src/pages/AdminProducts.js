import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api/axios";

export default function AdminProducts() {

  const [products, setProducts] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {

    try {

      const response = await api.get("/admin/products");

      setProducts(response.data);

    } catch (err) {

      console.log(err);
    }
  };

  const deleteProduct = async (id) => {

    const confirmed = window.confirm(
      "Delete this product?"
    );

    if (!confirmed) return;

    try {

      await api.delete(`/admin/products/${id}`);

      setProducts(prev =>
        prev.filter(product => product.id !== id)
      );

    } catch (err) {

      console.log(err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      <div className="flex justify-between items-center mb-6">

        <h1 className="text-3xl font-bold">
          Admin Products
        </h1>

        <button
          onClick={() => navigate("/admin/products/new")}
          className="bg-blue-600 text-white px-4 py-2 rounded"
        >
          Add Product
        </button>

      </div>

      <div className="bg-white rounded shadow overflow-hidden">

        <table className="w-full">

          <thead className="bg-gray-200">

            <tr>
              <th className="p-3 text-left">Name</th>
              <th className="p-3 text-left">Category</th>
              <th className="p-3 text-left">Price</th>
              <th className="p-3 text-left">Actions</th>
            </tr>

          </thead>

          <tbody>

            {products.map(product => (

              <tr
                key={product.id}
                className="border-t"
              >

                <td className="p-3">
                  {product.name}
                </td>

                <td className="p-3">
                  {product.category}
                </td>

                <td className="p-3">
                  ₹{product.price}
                </td>

                <td className="p-3 flex gap-2">

                  <button
                    onClick={() =>
                      navigate(`/admin/products/edit/${product.id}`)
                    }
                    className="bg-yellow-500 text-white px-3 py-1 rounded"
                  >
                    Edit
                  </button>

                  <button
                    onClick={() => deleteProduct(product.id)}
                    className="bg-red-600 text-white px-3 py-1 rounded"
                  >
                    Delete
                  </button>

                </td>

              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </div>
  );
}