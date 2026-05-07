import { api } from "./axios";

export const updateOrderStatus = (orderId, status) => {
  return api.patch(
    `orders/${orderId}/status?status=${status}`
  );
};