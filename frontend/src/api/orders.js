import { api } from "./axios";


export const getOrderStatuses = () => {
  return api.get("/admin/orders/statuses");
};

export const updateOrderStatus = (orderId, status) => {
  return api.patch(
    `/admin/orders/${orderId}/status?status=${status}`
  );
};