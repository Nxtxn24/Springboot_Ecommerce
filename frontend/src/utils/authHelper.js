import { parseJwt } from "./jwt";

export const getAuthUser = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  return parseJwt(token);
};