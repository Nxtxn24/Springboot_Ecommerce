import { useCallback, useEffect, useState } from "react";
import { api } from "../api/axios";
import StarRating from "../utils/starRating";

export default function Products() {

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");
  const [addingProductId, setAddingProductId] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [search, setSearch] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");

  // =========================
  // debounce
  // =========================
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(search);
      setPage(0);
    }, 500);

    return () => clearTimeout(timer);
  }, [search]);

  const highlightText = (text, query) => {
    if (!query) return text;

    const escapedQuery = query.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
    const regex = new RegExp(`(${escapedQuery})`, "gi");

    return text.split(regex).map((part, i) =>
      part.toLowerCase() === query.toLowerCase() ? (
        <span key={i} className="bg-yellow-200">
          {part}
        </span>
      ) : (
        part
      )
    );
  };


  const ProductSkeleton = () => (
    <div className="bg-white rounded-xl shadow p-4 animate-pulse">

      <div className="h-40 bg-gray-300 rounded mb-3"></div>

      <div className="h-4 bg-gray-300 w-3/4 mb-2"></div>
      <div className="h-4 bg-gray-300 w-1/2 mb-2"></div>

      <div className="h-6 bg-gray-300 w-1/3 mb-4"></div>

      <div className="h-8 bg-gray-300 rounded"></div>
    </div>
  );
  // =========================
  // fetch
  // =========================
  const fetchProducts = useCallback(async (signal) => {
    setLoading(true);
    setError("");

    try {
      const res = await api.get("/api/products", {
        params: { page, size: 8, search: debouncedSearch },
        signal,
      });

      const data = res.data;

      setProducts(data.content ?? data);
      setTotalPages(data.totalPages ?? 1);

    } catch (err) {
      if (err.code !== "ERR_CANCELED") {
        setError(err.response?.data?.message || "Unable to load products");
      }
    } finally {
      if (!signal?.aborted) {
        setLoading(false);
      }
    }
  }, [page, debouncedSearch]);

  useEffect(() => {
    const controller = new AbortController();
    fetchProducts(controller.signal);
    return () => controller.abort();
  }, [fetchProducts]);

  // =========================
  // clear search
  // =========================
  const clearSearch = () => {
    setSearch("");
    setDebouncedSearch("");
    setPage(0);
  };

  // =========================
  // cart
  // =========================
  const addToCart = async (id) => {
    setAddingProductId(id);
    setError("");
    setNotice("");

    try {
      await api.post(`/cart/add/${id}?quantity=1`);
      setNotice("Product added to your cart");
    } catch (err) {
      setError(err.response?.data?.message || "Unable to add this product to the cart");
    } finally {
      setAddingProductId(null);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      {/* TITLE */}
      <h2 className="text-2xl font-bold mb-6 text-center">
        Products
      </h2>

      {error && (
        <p role="alert" className="max-w-md mx-auto mb-4 text-center text-red-600">
          {error}
        </p>
      )}

      {notice && (
        <p role="status" className="max-w-md mx-auto mb-4 text-center text-green-700">
          {notice}
        </p>
      )}

      {/* SEARCH BAR */}
      <div className="max-w-md mx-auto mb-6 relative">

        {/* Search icon (SVG) */}
        <div className="absolute left-3 top-2.5 text-gray-400">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M21 21l-4.35-4.35m1.85-5.15a7 7 0 11-14 0 7 7 0 0114 0z"
            />
          </svg>
        </div>

        {/* input */}
        <input
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          placeholder="Search products..."
          className="w-full pl-10 pr-10 py-2 border rounded-md"
        />

        {/* clear button */}
        {search && (
          <button
            onClick={clearSearch}
            className="absolute right-3 top-2 text-gray-500 hover:text-black"
          >
            ✕
          </button>
        )}
      </div>

      {/* LOADING SKELETON */}
      {loading && (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {Array(8).fill(0).map((_, i) => (
            <ProductSkeleton key={i} />
          ))}
        </div>
      )}

      {/* PRODUCTS */}
      {!loading && (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">

          {products.length === 0 && (
            <p className="col-span-full text-center text-gray-500">
              No products found.
            </p>
          )}

          {products.map((p) => (
            <div
              key={p.id}
              className="bg-white rounded-xl shadow p-4 flex flex-col"
            >

              {/* image */}
              <img
                src={p.imageUrl || "/placeholder.png"}
                alt={p.name}
                className="h-40 object-cover rounded mb-3"
              />

              {/* name with highlight */}
              <h3 className="text-lg font-semibold">
                {highlightText(p.name, debouncedSearch)}
              </h3>

              {/* rating */}
              <div className="flex items-center gap-2 mb-2">
                <StarRating rating={p.averageRating || 0} readonly size={18} />
                <span className="text-sm text-gray-600">
                  ({p.ratingCount || 0})
                </span>
              </div>

              {/* price */}
              <p className="font-medium mb-4">
                ₹{p.price}
              </p>

              <div className="flex-grow" />

              {/* button */}
              <button
                onClick={() => addToCart(p.id)}
                disabled={addingProductId === p.id || p.stockQuantity === 0}
                className="bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-50"
              >
                {p.stockQuantity === 0
                  ? "Out of Stock"
                  : addingProductId === p.id
                    ? "Adding..."
                    : "Add to Cart"}
              </button>

            </div>
          ))}

        </div>
      )}

      {/* PAGINATION */}
      {totalPages > 1 && (
        <div className="flex justify-center gap-4 mt-8">

          <button
            disabled={page === 0}
            onClick={() => setPage(p => p - 1)}
            className="px-3 py-1 bg-gray-300 rounded disabled:opacity-50"
          >
            Prev
          </button>

          <span>
            Page {page + 1} / {totalPages}
          </span>

          <button
            disabled={page + 1 >= totalPages}
            onClick={() => setPage(p => p + 1)}
            className="px-3 py-1 bg-gray-300 rounded disabled:opacity-50"
          >
            Next
          </button>

        </div>
      )}

    </div>
  );
}
