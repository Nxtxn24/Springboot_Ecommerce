import { render, screen } from "@testing-library/react";
import App from "./App";

test("redirects visitors to the login page", async () => {
  render(<App />);
  expect(
    await screen.findByRole("heading", { name: /login to your account/i })
  ).toBeInTheDocument();
});
