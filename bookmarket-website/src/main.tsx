import "./index.css";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ShopPage } from "./pages/ShopPage";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { LoginPage } from "./pages/LoginPage";
import { SignUpPage } from "./pages/SignUpPage";
import { ViewListingPage } from "./pages/ViewListingPage";
import { CreateListingPage } from "./pages/CreateListingPage";

const router = createBrowserRouter([
	{
		path: "/",
		element: <ShopPage />
	},
	{
		path: "/login",
		element: <LoginPage />
	},
	{
		path: "/signup",
		element: <SignUpPage />
	},
	{
		path: "/listing/:id",
		element: <ViewListingPage />
	},
	{
		path: "/create-listing",
		element: <CreateListingPage />
	}
]);

createRoot(document.getElementById("root")!).render(
	<StrictMode>
		<RouterProvider router={router} />
	</StrictMode>
);
