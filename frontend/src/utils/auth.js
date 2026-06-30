import { parseJwt } from "./jwt";

export const getStoredUser = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const user = parseJwt(token);
  if (!user || !user.exp || user.exp * 1000 <= Date.now()) {
    logout();
    return null;
  }

  return user;
};

export const isLoggedIn = () => getStoredUser() !== null;

export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("email");
};
